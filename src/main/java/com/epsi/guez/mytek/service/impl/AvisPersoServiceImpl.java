package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.AvisPersoDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.service.AvisPersoService;
import org.springframework.stereotype.Service;

@Service
public class AvisPersoServiceImpl implements AvisPersoService {

    private AvisPersoDao avisPersoDao;

    public AvisPersoServiceImpl(AvisPersoDao avisPersoDao) {
        this.avisPersoDao = avisPersoDao;
    }

    @Override
    public void ajouterAvis(Long idUtilisateur, Long idFilm, String note, String avis, String aVoir) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connecte", "Vous devez être connecté pour rédiger un avis");
        }
        if(idFilm != null){

        }
        if (ex.mustBeThrown()) {
            throw ex;
        }

        // implémenter
        // avisPersoDao.save(new UtilisateurFilm());
    }
}
