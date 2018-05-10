package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InscriptionGroupe {

    private String nom;
    private String urlImage;
    private Date date;

    public InscriptionGroupe(String nom, String urlImage) {
        this.nom = nom;
        this.urlImage = urlImage;
        this.date = new Date();
    }
}
