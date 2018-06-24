package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurGroupeDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.UtilisateurGroupe;
import com.epsi.guez.mytek.service.UtilisateurGroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UtilisateurGroupeServiceImpl implements UtilisateurGroupeService {

    @Autowired
    private UtilisateurGroupeDao utilisateurGroupeDao;

    @Override
    public List<Long> findAllUtilisateurIdByGroupeId(Long id) {
        return utilisateurGroupeDao.findAllUtilisateurIdByGroupeId(id);
    }

    public List<Long> findAllAdminIdByGroupeId(Long id) {
        return utilisateurGroupeDao.findAllAdminIdByGroupeId(id);
    }

    public List<Long> findAllMembresIdByGroupeId(Long id) {
        return utilisateurGroupeDao.findAllMembresIdByGroupeId(id);
    }

    @Override
    public List<Long> findAllGroupesIdByUtilisateurId(Long id) {
        return utilisateurGroupeDao.findAllGroupesIdByAdminId(id);
    }

    @Override
    public List<Long> findAllGroupesIdByAdminId(Long id) {
        return utilisateurGroupeDao.findAllGroupesIdByAdminId(id);
    }

    @Override
    public List<Long> findAllGroupesIdByMembreId(Long id) {
        return utilisateurGroupeDao.findAllGroupesIdByMembreId(id);
    }

    @Override
    public int countByUtilisateurIdAndGroupeId(Long idUtilisateur, Long idGroupe) {
        return utilisateurGroupeDao.countByUtilisateurIdAndGroupeId(idUtilisateur, idGroupe);
    }

    @Override
    public void rendreMembreAdministrateur(Long idAdmin, Long idUtilisateur, Long idGroupe) throws MyTekException {
        MyTekException ex = new MyTekException();

        if (idAdmin != null && idUtilisateur != null && idGroupe != null) {
            // si l'admin a les droits sur le groupe
            if (utilisateurGroupeDao.countByUtilisateurIdAndGroupeIdAndDroit(idAdmin, idGroupe, true) == 0) {
                ex.addMessage("droits", "Vous n'avez pas les droits sur ce groupe.");
            }
            // si l'utilisateur appartient au groupe
            if (utilisateurGroupeDao.countByUtilisateurIdAndGroupeIdAndDroit(idUtilisateur, idGroupe, false) == 0) {
                ex.addMessage("utilisateur", "L'utilisateur n'appartient pas au groupe.");
            }
            // si le membre est n'est pas déjà admin
            if (utilisateurGroupeDao.countByUtilisateurIdAndGroupeIdAndDroit(idUtilisateur, idGroupe, true) == 1) {
                ex.addMessage("membre", "Le membre est déjà adminstrateur de ce groupe.");
            }
            if (ex.mustBeThrown()) {
                throw ex;
            }
            utilisateurGroupeDao.modifierDroitUtilisateur(idUtilisateur, idGroupe, true);
        }
    }

    @Override
    public void setGroupeUtilisateur(Long idUtilisateur, Long idGroupe, Long idDemandeur) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour accepter une demande.");
            throw ex;
        }
        if (!isAdministrateur(idUtilisateur, idGroupe)) {
            ex.addMessage("admin", "Vous n'êtes pas admin de ce groupe.");
        }
        if (countByUtilisateurIdAndGroupeId(idDemandeur, idGroupe) > 0) {
            ex.addMessage("groupe", "L'utilisateur appartient déjà à ce groupe.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        utilisateurGroupeDao.save(new UtilisateurGroupe(idDemandeur, idGroupe, false));
    }

    @Override
    public boolean isAdministrateur(Long idUtilisateur, Long idGroupe) {
        return utilisateurGroupeDao.countByUtilisateurIdAndGroupeIdAndDroit(idUtilisateur, idGroupe, true) > 0;
    }
}
