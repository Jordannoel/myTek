package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Acteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private String nationalite;

    @ManyToMany(mappedBy = "acteurs")
    private List<Film> films = new ArrayList<>();

    public Acteur() {
    }

    public Acteur(String nom, String prenom, String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }
}
