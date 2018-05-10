package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String titre;

    @NotNull
    private String affiche;

    private String nationalite;

    @Column(name = "titre_original")
    private String titreOriginal;

    private String genre;
    //private String realisateur;     //remplacer par List<Realisateur>
    //private String acteur;          //remplacer par List<Acteur>
    //private Date dateSortie;
    //private String note;            //remplacer par une table intermédiaire avec l'utilisateur
    //private String avisUtilisateur; //remplacer par une table intermédiaire avec l'utilisateur
    //private boolean vuOuAVoir;      //remplacer par une table intermédiaire avec l'utilisateur

    public Film(@NotNull String titre, @NotNull String affiche, String nationalite, String titreOriginal, String genre) {
        this.titre = titre;
        this.affiche = affiche;
        this.nationalite = nationalite;
        this.titreOriginal = titreOriginal;
        this.genre = genre;
    }

    public Film() {
    }

}