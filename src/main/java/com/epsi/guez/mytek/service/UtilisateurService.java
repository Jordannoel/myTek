package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    String findPrenomByEmail(String email);

    String findNomByEmail(String email);

    String findMotDePasseByEmail(String email);

    Utilisateur findOneById(Long id);

    Utilisateur findOneByEmail(String email);

    void setGroupeUtilisateur(Long idUtilisateur, Long idGroupe) throws MyTekException;

    List<Utilisateur> findAllByIdIn(List<Long> ids);

    void utilisateurConnecte(Long id) throws MyTekException;

    void utilisateurPeutVoirGroupe(Long idUtilisateur, Long idGroupe) throws MyTekException;
}
