package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.DemandeEnAttenteDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.DemandeEnAttente;
import com.epsi.guez.mytek.service.DemandeEnAttenteService;
import com.epsi.guez.mytek.service.UtilisateurGroupeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DemandeEnAttenteServiceImpl implements DemandeEnAttenteService {

    private DemandeEnAttenteDao demandeEnAttenteDao;

    private UtilisateurGroupeService utilisateurGroupeService;

    public DemandeEnAttenteServiceImpl(DemandeEnAttenteDao demandeEnAttenteDao, UtilisateurGroupeService utilisateurGroupeService) {
        this.demandeEnAttenteDao = demandeEnAttenteDao;
        this.utilisateurGroupeService = utilisateurGroupeService;
    }

    @Override
    public void demanderRejoindreGroupe(Long idUtilisateur, Long idGroupe) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour rejoindre un groupe.");
        }
        if (idGroupe == null) {
            ex.addMessage("groupe", "Le groupe n'est pas valide.");
        }
        if (demandeExistante(idUtilisateur, idGroupe)) {
            ex.addMessage("existant", "Vous avez déjà demandé à rejoindre ce groupe.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        demandeEnAttenteDao.save(new DemandeEnAttente(idUtilisateur, idGroupe));
    }

    public boolean demandeExistante(Long idUtilisateur, Long idGroupe) {
        return demandeEnAttenteDao.countByUtilisateurIdAndGroupeId(idUtilisateur, idGroupe) > 0;
    }

    @Override
    public List<Long> findAllUtilisateurEnAttenteForGroupe(Long idGroupe) {
        return demandeEnAttenteDao.findAllUtilisateurEnAttenteForGroupe(idGroupe);
    }

    @Override
    public void supprimerDemande(Long idUtilisateur, Long idDemandeur, Long idGroupe) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour refuser une demande.");
        }
        if (!utilisateurGroupeService.isAdministrateur(idUtilisateur, idGroupe)) {
            ex.addMessage("admin", "Vous n'êtes pas admin de ce groupe.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        demandeEnAttenteDao.deleteByUtilisateurIdAndGroupeId(idDemandeur, idGroupe);
    }
}
