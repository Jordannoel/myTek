package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;

public interface UtilisateurFilmService {
    void ajouterFilmAMediatheque(Long idUtilisateur, Long idFilm) throws MyTekException;

    boolean filmRattacheAUtilisateur(Long idUtilisateur, Long idFilm);
}
