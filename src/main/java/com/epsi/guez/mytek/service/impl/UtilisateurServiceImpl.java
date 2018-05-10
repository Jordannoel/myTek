package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurDao;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurDao utilisateurDao;

    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
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
}
