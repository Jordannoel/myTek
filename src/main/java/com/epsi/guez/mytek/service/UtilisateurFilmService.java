package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.UtilisateurFilm;

public interface UtilisateurFilmService {
    void ajouterFilmAMediatheque(Long idUtilisateur, Long idFilm) throws MyTekException;

    boolean filmRattacheAUtilisateur(Long idUtilisateur, Long idFilm);

    void ajouterAvis(Long idUtilisateur, Long idFilm, boolean vu, String avis, int note) throws MyTekException;

    UtilisateurFilm findByUtilisateurIdAndFilmId(Long idUtilisateur, Long idFilm);
}
