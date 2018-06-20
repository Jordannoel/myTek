package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.RealisateurDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Realisateur;
import com.epsi.guez.mytek.service.RealisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealisateurServiceImpl implements RealisateurService {

    private RealisateurDao realisateurDao;

    public RealisateurServiceImpl(RealisateurDao realisateurDao) {
        this.realisateurDao = realisateurDao;
    }

    @Override
    public List<Realisateur> findAll() {
        return realisateurDao.findAll();
    }

    @Override
    public void ajouterRealisateur(String nom, String prenom, String nationalite, Long idUtilisateur) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour ajouter un réalisateur.");
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
        realisateurDao.save(new Realisateur(nom, prenom, nationalite));
    }
}

