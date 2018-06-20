package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Realisateur;

import java.util.List;

public interface RealisateurService {

    List<Realisateur> findAll();

    void ajouterRealisateur(String nom, String prenom, String nationalite, Long idUtilisateur) throws MyTekException;
}
