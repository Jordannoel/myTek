package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titre;

    @NotNull
    private String affiche;

    private String nationalite;

    @Column(name = "titre_original")
    private String titreOriginal;

    private String genre;

    @ManyToMany
    @JoinTable(
            name = "film_realisateur",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "realisateur_id")}
    )
    private List<Realisateur> realisateurs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_acteur",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "acteur_id")}
    )
    private List<Acteur> acteurs = new ArrayList<>();

    @Column(name = "date_sortie")
    private Date dateSortie;

    public Film(@NotNull String titre, @NotNull String affiche, String nationalite, String titreOriginal, String genre) {
        this.titre = titre;
        this.affiche = affiche;
        this.nationalite = nationalite;
        this.titreOriginal = titreOriginal;
        this.genre = genre;
    }

    public Film(@NotNull String titre, @NotNull String affiche, String nationalite, String titreOriginal, String genre, Realisateur realisateur, Acteur acteur, Date dateSortie) {
        this.titre = titre;
        this.affiche = affiche;
        this.nationalite = nationalite;
        this.titreOriginal = titreOriginal;
        if (!genre.equals("0")) {
            this.genre = genre;
        }
        this.realisateurs.add(realisateur);
        this.acteurs.add(acteur);
        this.dateSortie = dateSortie;
    }

    public Film() {
    }

}