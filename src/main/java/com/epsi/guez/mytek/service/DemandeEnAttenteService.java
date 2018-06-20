package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;

public interface DemandeEnAttenteService {
    void demanderRejoindreGroupe(Long idUtilisateur, Long idGroupe) throws MyTekException;

    boolean demandeExistante(Long idUtilisateur, Long idGroupe);
}
