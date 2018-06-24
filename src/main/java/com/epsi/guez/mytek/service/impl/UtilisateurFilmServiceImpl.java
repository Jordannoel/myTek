package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.UtilisateurFilmDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.UtilisateurFilm;
import com.epsi.guez.mytek.service.UtilisateurFilmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        if (filmRattacheAUtilisateur(idUtilisateur, idFilm)) {
            ex.addMessage("deja", "Vous possedez déjà ce film dans votre médiathèque.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        utilisateurFilmDao.save(new UtilisateurFilm(idUtilisateur, idFilm));
    }

    @Override
    public boolean filmRattacheAUtilisateur(Long idUtilisateur, Long idFilm) {
        return utilisateurFilmDao.countByUtilisateurIdAndFilmId(idUtilisateur, idFilm) > 0;
    }

    @Override
    public void ajouterAvis(Long idUtilisateur, Long idFilm, boolean vu, String avis, int note) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
            ex.addMessage("connecte", "Vous devez être connecté pour rédiger un avis");
        }
        if (idFilm == null) {
            ex.addMessage("film", "Veuillez spécifier un film.");
        }
        if (!noteValide(note)) {
            ex.addMessage("note", "La note est invalide.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        utilisateurFilmDao.modifierAvisUtilisateur(idUtilisateur, idFilm, vu, avis, note);
    }

    private boolean noteValide(int note) {
        return note > 0 && note <= 5;
    }

    @Override
    public UtilisateurFilm findByUtilisateurIdAndFilmId(Long idUtilisateur, Long idFilm){
        return utilisateurFilmDao.findByUtilisateurIdAndFilmId(idUtilisateur, idFilm);
    }
}
