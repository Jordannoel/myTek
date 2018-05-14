package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String prenom;

    private String nom;

    private String email;

    private String motDePasse;

    @ManyToMany
    @JoinTable(
            name = "groupe_utilisateur",
            joinColumns = {@JoinColumn(name = "utilisateur_id")},
            inverseJoinColumns = {@JoinColumn(name = "groupe_id")}
    )
    private List<Groupe> groupes = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String prenom, String nom, String email, String motDePasse) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Utilisateur(String prenom, String nom, String email, String motDePasse, Groupe groupe) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.groupes.add(groupe);
    }

    public Utilisateur(String prenom, String nom, String email, String motDePasse, List<Groupe> groupes) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.groupes = groupes;
    }

    public void addGroupe(Groupe groupe){
        this.groupes.add(groupe);
    }
}