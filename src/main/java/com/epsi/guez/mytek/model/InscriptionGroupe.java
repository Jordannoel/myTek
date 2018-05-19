package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InscriptionGroupe {

    private String nom;
    private String urlImage;

    public InscriptionGroupe(String nom, String urlImage) {
        this.nom = nom;
        this.urlImage = urlImage;
    }
}
