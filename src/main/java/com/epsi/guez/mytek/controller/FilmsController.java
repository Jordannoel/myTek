package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Acteur;
import com.epsi.guez.mytek.model.Film;
import com.epsi.guez.mytek.model.Realisateur;
import com.epsi.guez.mytek.model.UtilisateurFilm;
import com.epsi.guez.mytek.model.enums.GenreEnum;
import com.epsi.guez.mytek.service.ActeurService;
import com.epsi.guez.mytek.service.FilmService;
import com.epsi.guez.mytek.service.RealisateurService;
import com.epsi.guez.mytek.service.UtilisateurFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class FilmsController {

    private static final String REDIRECT = "redirect:";

    @Autowired
    private FilmService filmService;

    @Autowired
    private RealisateurService realisateurService;

    @Autowired
    private ActeurService acteurService;

    @Autowired
    private UtilisateurFilmService utilisateurFilmService;

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM, method = RequestMethod.GET)
    public String ajouterFilm(ModelMap modelMap) {
        List<GenreEnum> genres = Arrays.asList(GenreEnum.values());
        List<Realisateur> realisateurs = realisateurService.findAll();
        List<Acteur> acteurs = acteurService.findAll();
        modelMap.put("realisateurs", realisateurs);
        modelMap.put("acteurs", acteurs);
        modelMap.put("genres", genres);
        return PageMapping.AJOUTER_FILM;
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM, method = RequestMethod.POST)
    public String ajouterFilmPost(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        String titre = req.getParameter("titre");
        String affiche = req.getParameter("affiche");
        String nationalite = req.getParameter("nationalite");
        String titreOriginal = req.getParameter("titreOriginal");
        String genre = req.getParameter("genre");
        Long idRealisateur = Long.valueOf(req.getParameter("realisateurs"));
        Long idActeur = Long.valueOf(req.getParameter("acteurs"));
        String dateSortie = req.getParameter("dateSortie");
        HttpSession session = req.getSession();
        try {
            Long idUtilisateur = (Long) session.getAttribute("id");
            filmService.ajouterFilm(titre, affiche, nationalite, titreOriginal, genre, idUtilisateur, idRealisateur, idActeur, dateSortie);
            redirectAttributes.addFlashAttribute("success", "Votre film a bien été ajouté");
            return REDIRECT + ApplicationUrl.AJOUTER_FILM;
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.AJOUTER_FILM;
        }
    }

    @RequestMapping(value = ApplicationUrl.VOIR_FILMS, method = RequestMethod.GET)
    public String voirFilm(HttpServletRequest req, ModelMap modelMap) {
        List<Film> films = filmService.findAll();
        modelMap.put("films", films);
        return PageMapping.VOIR_FILMS;
    }

    @RequestMapping(path = ApplicationUrl.VOIR_FILMS_LISTE, method = RequestMethod.GET)
    @ResponseBody
    public List<Film> voirFilmListe() {
        return filmService.findAll();
    }

    @RequestMapping(value = ApplicationUrl.FILM + "/{idFilm}", method = RequestMethod.GET)
    public String film(HttpServletRequest req, ModelMap modelMap, @PathVariable(value = "idFilm") Long idFilm) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        Film film = filmService.findById(idFilm);
        boolean avisPossible = utilisateurFilmService.filmRattacheAUtilisateur(idUtilisateur, idFilm);
        if (avisPossible) {
            UtilisateurFilm avisExistant = utilisateurFilmService.findByUtilisateurIdAndFilmId(idUtilisateur, idFilm);
            modelMap.put("note", avisExistant.getNote());
            modelMap.put("avis", avisExistant.getAvis());
            modelMap.put("vu", avisExistant.isVu());
        } else {
            modelMap.put("note", 0);
            modelMap.put("avis", "");
        }

        modelMap.put("film", film);
        modelMap.put("realisateurs", film.getRealisateurs());
        modelMap.put("acteurs", film.getActeurs());
        modelMap.put("avisPossible", avisPossible);
        return PageMapping.FILM;
    }

    @RequestMapping(value = ApplicationUrl.NOTER_FILM + "/" + "{idFilm}", method = RequestMethod.POST)
    public String noterFilm(HttpServletRequest req, ModelMap modelMap, @PathVariable(value = "idFilm") Long idFilm, RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        String note = req.getParameter("note");
        String avis = req.getParameter("avis");
        boolean vu = ("vu").equals(req.getParameter("vu"));
        try {
            utilisateurFilmService.ajouterAvis(idUtilisateur, idFilm, vu, avis, Integer.valueOf(note));
            redirectAttributes.addFlashAttribute("success", "Votre avis a bien été ajouté.");
            return REDIRECT + ApplicationUrl.FILM + "/" + idFilm;
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.FILM + "/" + idFilm;
        }
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_REALISATEUR, method = RequestMethod.GET)
    public String ajouterRealisateur() {
        return PageMapping.AJOUTER_REALISATEUR;
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_REALISATEUR, method = RequestMethod.POST)
    public String ajouterRealisateurPost(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String nationalite = req.getParameter("nationalite");
        HttpSession session = req.getSession();
        try {
            Long idUtilisateur = (Long) session.getAttribute("id");
            realisateurService.ajouterRealisateur(nom, prenom, nationalite, idUtilisateur);
            redirectAttributes.addFlashAttribute("success", "Votre réalisateur a bien été ajouté.");
            return REDIRECT + ApplicationUrl.AJOUTER_REALISATEUR;
        } catch (MyTekException ex) {
            modelMap.put("errors", ex.getMessages());
            return PageMapping.AJOUTER_REALISATEUR;
        }
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_ACTEUR, method = RequestMethod.GET)
    public String ajouterActeur() {
        return PageMapping.AJOUTER_ACTEUR;
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_ACTEUR, method = RequestMethod.POST)
    public String ajouterActeurPost(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String nationalite = req.getParameter("nationalite");
        HttpSession session = req.getSession();
        try {
            Long idUtilisateur = (Long) session.getAttribute("id");
            acteurService.ajouterActeur(nom, prenom, nationalite, idUtilisateur);
            redirectAttributes.addFlashAttribute("success", "Votre acteur a bien été ajouté");
            return REDIRECT + ApplicationUrl.AJOUTER_ACTEUR;
        } catch (MyTekException ex) {
            modelMap.put("errors", ex.getMessages());
            return PageMapping.AJOUTER_ACTEUR;
        }
    }
}