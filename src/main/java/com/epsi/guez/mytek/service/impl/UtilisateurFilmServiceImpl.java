package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurFilmDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.UtilisateurFilm;
import com.epsi.guez.mytek.service.UtilisateurFilmService;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurFilmServiceImpl implements UtilisateurFilmService {

    private UtilisateurFilmDao utilisateurFilmDao;

    public UtilisateurFilmServiceImpl(UtilisateurFilmDao utilisateurFilmDao) {
        this.utilisateurFilmDao = utilisateurFilmDao;
    }

    @Override
    public void ajouterFilmAMediatheque(Long idUtilisateur, Long idFilm) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connexion", "Vous devez être connecté pour ajouter un film à votre médiathèque.");
        }
        if (idFilm == null) {
            ex.addMessage("film", "Vous devez sélectionner un film.");
        }
        if(filmRattacheAUtilisateur(idUtilisateur, idFilm)){
            ex.addMessage("deja", "Vous possedez déjà ce film dans votre médiathèque.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }

        utilisateurFilmDao.save(new UtilisateurFilm(idUtilisateur, idFilm));
    }

    @Override
    public boolean filmRattacheAUtilisateur(Long idUtilisateur, Long idFilm) {
        int test = utilisateurFilmDao.countByUtilisateurIdAndFilmId(idUtilisateur, idFilm);
        return test > 0;
    }
}
