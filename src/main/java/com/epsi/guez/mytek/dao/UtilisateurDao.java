package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

    int countByEmail(String email);

    List<Utilisateur> findByEmail(String email);

    Utilisateur findOneById(Long id);

    @Query("SELECT distinct u FROM Utilisateur u WHERE u.id in :ids ")
    List<Utilisateur> findAllByIdIn(@Param("ids") List<Long> ids);
}
