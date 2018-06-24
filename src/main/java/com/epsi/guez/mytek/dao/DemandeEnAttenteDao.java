package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.DemandeEnAttente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeEnAttenteDao extends JpaRepository<DemandeEnAttente, Long> {
    int countByUtilisateurIdAndGroupeId(Long idUtilisateur, Long idGroupe);

    @Query(value = "select d.utilisateurId from DemandeEnAttente d where d.groupeId = :idGroupe")
    List<Long> findAllUtilisateurEnAttenteForGroupe(@Param(value = "idGroupe") Long idGroupe);

    void deleteByUtilisateurIdAndGroupeId(@Param(value = "idUtilisateur") Long idUtilisateur, @Param(value = "idGroupe") Long idGroupe);
}
