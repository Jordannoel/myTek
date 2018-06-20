package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.UtilisateurFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurFilmDao extends JpaRepository<UtilisateurFilm, Long> {

    int countByUtilisateurIdAndFilmId(Long idUtilisateur, Long idFilm);
}
