package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "utilisateur_groupe")
public class UtilisateurGroupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @NotNull
    @Column(name = "groupe_id")
    private Long groupeId;

    @NotNull
    private boolean droit;

    public UtilisateurGroupe() {

    }

    public UtilisateurGroupe(Long utilisateurId, Long groupeId, boolean droit) {
        this.utilisateurId = utilisateurId;
        this.groupeId = groupeId;
        this.droit = droit;
    }
}
