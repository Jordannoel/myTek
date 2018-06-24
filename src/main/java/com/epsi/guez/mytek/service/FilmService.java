package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(Long id);

    void ajouterFilm(String titre, String affiche, String nationalite, String titreOriginal, String genre, Long idUtilisateur, Long idRealisateur, Long idActeur) throws MyTekException;

    List<Film> findAllByIdUtilisateur(Long id);
}
