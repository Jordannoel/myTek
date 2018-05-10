package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.model.Utilisateur;

public interface UtilisateurService {

    String findPrenomByEmail(String email);

    String findNomByEmail(String email);

    String findMotDePasseByEmail(String email);

    Utilisateur findOneById(Long id);

    Utilisateur findOneByEmail(String email);
}
