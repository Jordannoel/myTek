package com.epsi.guez.mytek.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "demande_en_attente")
public class DemandeEnAttente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @NotNull
    @Column(name = "groupe_id")
    private Long groupeId;

    public DemandeEnAttente() {
    }

    public DemandeEnAttente(@NotNull Long utilisateurId, @NotNull Long groupeId) {
        this.utilisateurId = utilisateurId;
        this.groupeId = groupeId;
    }
}
