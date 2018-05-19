package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.UtilisateurGroupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurGroupeDao extends JpaRepository<UtilisateurGroupe, Long> {

    @Query(value = "select distinct utilisateurId from UtilisateurGroupe ug where ug.groupeId = :id")
    List<Long> findAllUtilisateurIdByGroupeId(@Param("id") Long id);

    @Query(value = "select distinct groupeId from UtilisateurGroupe ug where ug.utilisateurId = :id")
    List<Long> findAllGroupesIdByUtilisateurId(@Param("id") Long id);

    @Query(value = "select distinct utilisateurId from UtilisateurGroupe ug where ug.groupeId = :id and ug.droit = 1")
    List<Long> findAllAdminIdByGroupeId(@Param("id") Long id);

    @Query(value = "select distinct utilisateurId from UtilisateurGroupe ug where ug.groupeId = :id and ug.droit = 0")
    List<Long> findAllMembresIdByGroupeId(@Param("id") Long id);

    @Query(value = "select distinct groupeId from UtilisateurGroupe ug where ug.utilisateurId = :id and ug.droit = 1")
    List<Long> findAllGroupesIdByAdminId(@Param("id") Long id);

    @Query(value = "select distinct groupeId from UtilisateurGroupe ug where ug.utilisateurId = :id and ug.droit = 0")
    List<Long> findAllGroupesIdByMembreId(@Param("id") Long id);

    int countByUtilisateurIdAndGroupeId(Long idUtilisateur, Long idGroupe);

    int countByUtilisateurIdAndGroupeIdAndDroit(Long idUtilisateur, Long idGroupe, boolean droit);

    @Modifying(clearAutomatically = true)
    @Query(value = "update UtilisateurGroupe ug set ug.droit = :droit where ug.utilisateurId = :idUtilisateur and ug.groupeId = :idGroupe")
    void modifierDroitUtilisateur(@Param("idUtilisateur") Long idUtilisateur, @Param("idGroupe") Long idGroupe, @Param("droit") boolean droit);
}
