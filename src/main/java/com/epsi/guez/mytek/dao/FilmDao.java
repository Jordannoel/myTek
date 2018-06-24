package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmDao extends JpaRepository<Film, Long> {

    List<Film> findAll();

    Film findOneById(Long id);

    @Query(value = "select * from film" +
            " inner join utilisateur_film on film.id = utilisateur_film.film_id " +
            " inner join utilisateur on utilisateur_film.utilisateur_id = utilisateur.id" +
            " where utilisateur.id = :idUtilisateur", nativeQuery = true)
    List<Film> findAllByIdUtilisateur(@Param("idUtilisateur") Long idUtilisateur);
}