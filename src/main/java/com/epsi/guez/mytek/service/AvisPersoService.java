package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;

public interface AvisPersoService {
    void ajouterAvis(Long idUtilisateur, Long idFilm, String note, String avis, String aVoir) throws MyTekException;

}
