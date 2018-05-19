package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Utilisateur;

public interface ConnexionService {
    Utilisateur connecterUtilisateur(String email, String motDePasse) throws MyTekException;
}
