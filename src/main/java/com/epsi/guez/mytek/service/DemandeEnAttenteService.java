package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;

import java.util.List;

public interface DemandeEnAttenteService {
    void demanderRejoindreGroupe(Long idUtilisateur, Long idGroupe) throws MyTekException;

    boolean demandeExistante(Long idUtilisateur, Long idGroupe);

    List<Long> findAllUtilisateurEnAttenteForGroupe(Long idGroupe);

    void supprimerDemande(Long idUtilisateur, Long idDemandeur, Long idGroupe) throws MyTekException;
}
