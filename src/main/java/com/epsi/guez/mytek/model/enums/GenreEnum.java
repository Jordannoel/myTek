package com.epsi.guez.mytek.model.enums;

import lombok.Getter;

@Getter
public enum GenreEnum {
    ACTION("Action"),
    ANIMATION("Animation"),
    AVENTURE("Aventure"),
    BIOGRAPHIQUE("Biographique"),
    CATASTROPHE("Catastrophe"),
    COMEDIE("Comédie"),
    COMEDIE_DRAMATIQUE("Comédie dramatique"),
    COMEDIE_MUSICALE("Comédie musicale"),
    COMEDIE_POLICIERE("Comédie policière"),
    COMEDIE_ROMANTIQUE("Comédie romantique"),
    COURT_METRAGE("Court métrage"),
    DESSIN_ANIME("Dessin animé"),
    DOCUMENTAIRE("Documentaire"),
    DRAME("Drame"),
    DRAME_PSYCHOLOGIQUE("Drame psychologique"),
    EPOUVANTE("Epouvante"),
    EROTIQUE("Erotique"),
    EROTIQUE_X("Erotique X"),
    ESPIONNAGE("Espionnage"),
    ESSAI("Essai"),
    FANTASTIQUE("Fantastique"),
    FILM_A_SKETCHES("Film à sketches"),
    FILM_MUSICAL("Film musical"),
    GUERRE("Guerre"),
    HISTORIQUE("Historique"),
    HORREUR("Horreur"),
    KARATE("Karaté"),
    MANGA("Manga"),
    MELODRAME("Mélodrame"),
    MUET("Muet"),
    PAR_PARTIES("Par parties"),
    PEPLUM("Péplum"),
    POLICIER("Policier"),
    POLITIQUE("Politique"),
    PROGRAMME("Programme"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science fiction"),
    SERIAL("Sérial"),
    SPECTACLE("Spectacle"),
    TELEFILM("Téléfilm"),
    THEATRE("Théâtre"),
    THRILLER("Thriller"),
    WESTERN("Western");

    private final String libelle;

    GenreEnum(String libelle) {
        this.libelle = libelle;
    }

}
