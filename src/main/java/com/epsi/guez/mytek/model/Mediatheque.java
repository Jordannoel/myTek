package com.epsi.guez.mytek.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Mediatheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nomGroupe;

    @ManyToMany(mappedBy = "groupes")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

}
