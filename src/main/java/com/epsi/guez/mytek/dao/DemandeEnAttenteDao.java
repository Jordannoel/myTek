package com.epsi.guez.mytek.dao;

import com.epsi.guez.mytek.model.DemandeEnAttente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeEnAttenteDao extends JpaRepository<DemandeEnAttente, Long> {
    int countByUtilisateurIdAndGroupeId(Long idUtilisateur, Long idGroupe);

}
