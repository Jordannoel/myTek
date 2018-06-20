package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe représentant l'avis d'un utilisateur.
 * Si le film est dans la médiathèque de l'utilisateur, son avis peut être null ou non.
 */

@Entity
@Getter
@Setter
@Table(name = "utilisateur_film")
public class UtilisateurFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @NotNull
    @Column(name = "film_id")
    private Long filmId;

    private boolean vu;

    @Size(max = 140)
    private String avis;

    @Max(5)
    private int note;

    public UtilisateurFilm() {

    }

    public UtilisateurFilm(@NotNull Long utilisateurId, @NotNull Long filmId, boolean vu, String avis, int note) {
        this.utilisateurId = utilisateurId;
        this.filmId = filmId;
        this.vu = vu;
        this.avis = avis;
        this.note = note;
    }

    public UtilisateurFilm(Long idUtilisateur, Long idFilm) {
        this.utilisateurId = idUtilisateur;
        this.filmId = idFilm;
    }
}
