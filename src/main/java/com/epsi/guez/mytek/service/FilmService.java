package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(Long id);

    void ajouterFilm(String titre, String affiche, String nationalite, String titreOriginal, String genre) throws FormInvalideException;
}
