package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Film;
import com.epsi.guez.mytek.model.enums.GenreEnum;
import com.epsi.guez.mytek.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class FilmsController {

    private static final String REDIRECT = "redirect:";

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM, method = RequestMethod.GET)
    public String ajouterFilm(ModelMap modelMap) {
        List<GenreEnum> genres = Arrays.asList(GenreEnum.values());
        modelMap.put("genres", genres);
        modelMap.put("errors", new HashMap<>());
        return PageMapping.AJOUTER_FILM;
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM, method = RequestMethod.POST)
    public String ajouterFilmPost(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        String titre = req.getParameter("titre");
        String affiche = req.getParameter("affiche");
        String nationalite = req.getParameter("nationalite");
        String titreOriginal = req.getParameter("titreOriginal");
        String genre = req.getParameter("genre");
        try {
            filmService.ajouterFilm(titre, affiche, nationalite, titreOriginal, genre);
            redirectAttributes.addFlashAttribute("success", "Votre film a bien été ajouté");
            return REDIRECT + ApplicationUrl.AJOUTER_FILM;
        } catch (FormInvalideException ex) {
            modelMap.put("errors", ex.getMessages());
            return PageMapping.AJOUTER_FILM;
        }
    }

    @RequestMapping(value = ApplicationUrl.VOIR_FILMS, method = RequestMethod.GET)
    public String voirFilm(HttpServletRequest req, ModelMap modelMap) {
        List<Film> films = filmService.findAll();
        modelMap.put("films", films);
        return PageMapping.VOIR_FILMS;
    }

    @RequestMapping(value = ApplicationUrl.FILM + "/{idFilm}", method = RequestMethod.GET)
    public String film(HttpServletRequest req, ModelMap modelMap, @PathVariable(value = "idFilm") Long idFilm) {
        Film film = filmService.findById(idFilm);
        modelMap.put("film", film);
        return PageMapping.FILM;
    }



}
