package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nomGroupe;

    @Column(name = "image")
    private String urlImage;

    public Groupe() {
    }

    public Groupe(String nomGroupe, String urlImage) {
        this.nomGroupe = nomGroupe;
        this.urlImage = urlImage;
    }
}
