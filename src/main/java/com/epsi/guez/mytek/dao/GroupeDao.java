package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeDao extends JpaRepository<Groupe, Long> {

    List<Groupe> findAll();

    Groupe findOneById(Long id);

    Groupe findOneByNomGroupe(String nomGroupe);

    int countByNomGroupe(String nomGroupe);
}
