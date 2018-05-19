package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.Utils.MyTekUtils;
import com.epsi.guez.mytek.dao.FilmDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Film;
import com.epsi.guez.mytek.service.FilmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmDao filmDao;

    public FilmServiceImpl(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public List<Film> findAll() {
        return filmDao.findAll();
    }

    @Override
    public Film findById(Long id) {
        return filmDao.findOneById(id);
    }

    @Override
    public void ajouterFilm(String titre, String affiche, String nationalite, String titreOriginal, String genre, Long idUtilisateur) throws MyTekException {
        MyTekException ex = new MyTekException();
        if(idUtilisateur == null){
            ex.addMessage("connexion", "Vous devez êtrer connecté pour ajouter un film.");
        }
        if (titre == null || titre.equals("")) {
            ex.addMessage("titre", "Veuillez saisir un titre");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        if (affiche == null || affiche.equals("")) {
            affiche = MyTekUtils.getProperty("aucuneImage");
        }
        if (genre.equals("0")) {
            filmDao.save(new Film(titre, affiche, nationalite, titreOriginal, null));
        } else {
            filmDao.save(new Film(titre, affiche, nationalite, titreOriginal, genre));
        }
    }
}
