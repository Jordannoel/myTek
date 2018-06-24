package com.epsi.guez.mytek.service;

import com.epsi.guez.mytek.exception.MyTekException;

import java.util.List;

public interface UtilisateurGroupeService {
    List<Long> findAllUtilisateurIdByGroupeId(Long id);

    List<Long> findAllAdminIdByGroupeId(Long id);

    List<Long> findAllMembresIdByGroupeId(Long id);

    List<Long> findAllGroupesIdByUtilisateurId(Long id);

    List<Long> findAllGroupesIdByAdminId(Long id);

    List<Long> findAllGroupesIdByMembreId(Long id);

    int countByUtilisateurIdAndGroupeId(Long idUtilisateur, Long idGroupe);

    void rendreMembreAdministrateur(Long idAdmin, Long idUtilisateur, Long idGroupe) throws MyTekException;

    void setGroupeUtilisateur(Long idUtilisateur, Long idGroupe, Long idDemandeur) throws MyTekException;

    boolean isAdministrateur(Long idUtilisateur, Long idGroupe);

}
