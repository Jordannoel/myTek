package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActeurDao extends JpaRepository<Acteur, Long> {
    List<Acteur> findAll();

    Acteur findOneById(Long idActeur);
}
