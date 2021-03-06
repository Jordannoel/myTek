package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.Utils.MyTekUtils;
import com.epsi.guez.mytek.dao.ActeurDao;
import com.epsi.guez.mytek.dao.FilmDao;
import com.epsi.guez.mytek.dao.RealisateurDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Acteur;
import com.epsi.guez.mytek.model.Film;
import com.epsi.guez.mytek.model.Realisateur;
import com.epsi.guez.mytek.service.FilmService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class FilmServiceImpl implements FilmService {

    private FilmDao filmDao;

    private RealisateurDao realisateurDao;

    private ActeurDao acteurDao;

    public FilmServiceImpl(FilmDao filmDao, RealisateurDao realisateurDao, ActeurDao acteurDao) {
        this.filmDao = filmDao;
        this.realisateurDao = realisateurDao;
        this.acteurDao = acteurDao;
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
    public void ajouterFilm(String titre, String affiche, String nationalite, String titreOriginal, String genre, Long idUtilisateur, Long idRealisateur, Long idActeur, String date) throws MyTekException {
        MyTekException ex = new MyTekException();
        if (idUtilisateur == null) {
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateParsed = null;
        try {
            dateParsed = sdf.parse(date);
        } catch (ParseException e) {
            ex.addMessage("technique", "Une erreur est survenue : " + e.getMessage());
        }

        Realisateur realisateur = realisateurDao.findOneById(idRealisateur);
        Acteur acteur = acteurDao.findOneById(idActeur);

        filmDao.save(new Film(titre, affiche, nationalite, titreOriginal, genre, realisateur, acteur, dateParsed));
    }

    @Override
    public List<Film> findAllByIdUtilisateur(Long id) {
        return filmDao.findAllByIdUtilisateur(id);
    }
}
