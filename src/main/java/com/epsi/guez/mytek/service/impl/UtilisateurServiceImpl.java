package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurDao;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Groupe;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.GroupeService;
import com.epsi.guez.mytek.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurDao utilisateurDao;

    private GroupeService groupeService;

    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, GroupeService groupeService) {
        this.utilisateurDao = utilisateurDao;
        this.groupeService = groupeService;
    }

    @Override
    public String findPrenomByEmail(String email) {
        List<Utilisateur> utilisateurs = utilisateurDao.findByEmail(email);
        if (utilisateurs.size() == 0) {
            return null;
        } else {
            return utilisateurs.get(0).getPrenom();
        }
    }

    @Override
    public String findNomByEmail(String email) {
        List<Utilisateur> utilisateurs = utilisateurDao.findByEmail(email);
        if (utilisateurs.size() == 0) {
            return null;
        } else {
            return utilisateurs.get(0).getNom();
        }
    }

    @Override
    public String findMotDePasseByEmail(String email) {
        List<Utilisateur> utilisateurs = utilisateurDao.findByEmail(email);
        if (utilisateurs.size() == 0) {
            return null;
        } else {
            return utilisateurs.get(0).getMotDePasse();
        }
    }

    @Override
    public Utilisateur findOneById(Long id) {
        return utilisateurDao.findOneById(id);
    }

    @Override
    public Utilisateur findOneByEmail(String email) {
        List<Utilisateur> utilisateurs = utilisateurDao.findByEmail(email);
        if (utilisateurs.size() == 0) {
            return null;
        } else {
            return utilisateurs.get(0);
        }
    }

    @Override
    public void setGroupeUtilisateur(Long idUtilisateur, Long idGroupe) throws FormInvalideException {
        FormInvalideException ex = new FormInvalideException();

        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour rejoindre un groupe.");
            throw ex;
        }
        Utilisateur utilisateur = utilisateurDao.findOneById(idUtilisateur);
        Groupe groupe = groupeService.findOneById(idGroupe);
        if (utilisateur.getGroupes().contains(groupe)) {
            ex.addMessage("groupe", "Vous appartenez déjà à ce groupe.");
        }

        if (ex.mustBeThrown()) {
            throw ex;
        }

        utilisateur.addGroupe(groupe);
        utilisateurDao.save(utilisateur);
    }

}
