package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.model.Groupe;

import java.util.List;

public interface GroupeService {

    List<Groupe> findAll();

    Groupe findOneById(Long id);

    Groupe findOneByNomGroupe(String nomGroupe);

    boolean nomGroupeDejaExistant(String nomGroupe);

    List<Groupe> findAllGroupeAdministres(Long idAdmin);

    List<Groupe> findAllGroupeAppartenant(Long idUtilisateur);

    List<Groupe> findAllByIdIn(List<Long> ids);
}
