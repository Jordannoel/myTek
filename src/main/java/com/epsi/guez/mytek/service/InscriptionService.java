package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.InscriptionGroupe;
import com.epsi.guez.mytek.model.Utilisateur;

public interface InscriptionService {
    void inscrireUtilisateur(String email, String prenom, String nom, String motDePasse, String confirmationMotDePasse, Long idGroupe, boolean approbation) throws MyTekException;

    InscriptionGroupe inscrireGroupe(String nomGroupe, String urlImage, boolean approbation, Utilisateur createur) throws MyTekException;
}
