package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.UtilisateurFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisPersoDao extends JpaRepository<UtilisateurFilm, Long> {

}
