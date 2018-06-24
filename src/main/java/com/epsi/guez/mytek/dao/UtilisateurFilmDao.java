package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.UtilisateurFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurFilmDao extends JpaRepository<UtilisateurFilm, Long> {

    int countByUtilisateurIdAndFilmId(Long idUtilisateur, Long idFilm);

    UtilisateurFilm findByUtilisateurIdAndFilmId(Long idUtilisateur, Long idFilm);

    @Modifying(clearAutomatically = true)
    @Query(value = "update UtilisateurFilm uf set uf.vu = :vu, uf.avis = :avis, uf.note = :note where uf.utilisateurId = :idUtilisateur and uf.filmId = :idFilm")
    void modifierAvisUtilisateur(@Param("idUtilisateur") Long idUtilisateur, @Param("idFilm") Long idFilm, @Param("vu") boolean vu, @Param("avis") String avis, @Param("note") int note);
}
