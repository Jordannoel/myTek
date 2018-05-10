package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmDao extends JpaRepository<Film, Long> {

    List<Film> findAll();

    Film findOneById(Long id);
}
