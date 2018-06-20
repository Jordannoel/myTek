package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Acteur;

import java.util.List;

public interface ActeurService {
    List<Acteur> findAll();

    void ajouterActeur(String nom, String prenom, String nationalite, Long idUtilisateur) throws MyTekException;
}
