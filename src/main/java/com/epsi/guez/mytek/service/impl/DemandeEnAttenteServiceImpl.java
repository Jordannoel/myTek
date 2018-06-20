package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.DemandeEnAttenteDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.DemandeEnAttente;
import com.epsi.guez.mytek.service.DemandeEnAttenteService;
import org.springframework.stereotype.Service;

@Service
public class DemandeEnAttenteServiceImpl implements DemandeEnAttenteService {

    private DemandeEnAttenteDao demandeEnAttenteDao;

    public DemandeEnAttenteServiceImpl(DemandeEnAttenteDao demandeEnAttenteDao) {
        this.demandeEnAttenteDao = demandeEnAttenteDao;
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
}
