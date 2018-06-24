package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurDao;
import com.epsi.guez.mytek.dao.UtilisateurGroupeDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.model.UtilisateurGroupe;
import com.epsi.guez.mytek.service.GroupeService;
import com.epsi.guez.mytek.service.UtilisateurGroupeService;
import com.epsi.guez.mytek.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurDao utilisateurDao;

    private UtilisateurGroupeDao utilisateurGroupeDao;

    private GroupeService groupeService;

    private UtilisateurGroupeService utilisateurGroupeService;

    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, UtilisateurGroupeDao utilisateurGroupeDao, GroupeService groupeService, UtilisateurGroupeService utilisateurGroupeService) {
        this.utilisateurDao = utilisateurDao;
        this.utilisateurGroupeDao = utilisateurGroupeDao;
        this.groupeService = groupeService;
        this.utilisateurGroupeService = utilisateurGroupeService;
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
    public List<Utilisateur> findAllByIdIn(List<Long> ids) {
        return utilisateurDao.findAllByIdIn(ids);
    }

    @Override
    public void utilisateurConnecte(Long id) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (id == null) {
            ex.addMessage("connexion", "Vous n'êtes pas connecté");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
    }

    @Override
    public void utilisateurPeutVoirGroupe(Long idUtilisateur, Long idGroupe) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (utilisateurGroupeService.countByUtilisateurIdAndGroupeId(idUtilisateur, idGroupe) == 0) {
            ex.addMessage("permission", "Vous ne faites pas partie de ce groupe");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
    }


}
