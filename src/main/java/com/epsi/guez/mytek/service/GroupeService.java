package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.model.Groupe;

import java.util.List;

public interface GroupeService {

    List<Groupe> findAll();

    Groupe findOneById(Long id);

    public Groupe findOneByNomGroupe(String nomGroupe);

    boolean nomGroupeDejaExistant(String nomGroupe);
}
