package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.ActeurDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Acteur;
import com.epsi.guez.mytek.service.ActeurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActeurServiceImpl implements ActeurService {
    private ActeurDao acteurDao;

    public ActeurServiceImpl(ActeurDao acteurDao) {
        this.acteurDao = acteurDao;
    }

    @Override
    public List<Acteur> findAll() {
        return acteurDao.findAll();
    }

    @Override
    public void ajouterActeur(String nom, String prenom, String nationalite, Long idUtilisateur) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour ajouter un acteur.");
        }
        if (nom == null || nom.equals("")) {
            ex.addMessage("nom", "Veuillez saisir un nom");
        }
        if (prenom == null || prenom.equals("")) {
            ex.addMessage("prenom", "Veuillez saisir un prénom");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        acteurDao.save(new Acteur(nom, prenom, nationalite));
    }
}
