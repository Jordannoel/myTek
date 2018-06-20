package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Realisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealisateurDao extends JpaRepository<Realisateur, Long> {
    List<Realisateur> findAll();

    Realisateur findOneById(Long idRealisateur);
}
