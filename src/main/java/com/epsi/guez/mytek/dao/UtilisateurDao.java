package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

    int countByEmail(String email);

    List<Utilisateur> findByEmail(String email);

    Utilisateur findOneById(Long id);

    /*@Modifying
    @Query("update Utilisateur u set u. = ?1, u.lastname = ?2 where u.id = ?1")
    void setUtilisateurGroupeById(Long id, String nomGroupe);*/
}
