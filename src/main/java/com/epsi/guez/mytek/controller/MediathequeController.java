package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Film;
import com.epsi.guez.mytek.service.FilmService;
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
import java.util.List;

@Controller
public class MediathequeController {

    private static final String REDIRECT = "redirect:";

    @Autowired
    private UtilisateurFilmService utilisateurFilmService;

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = ApplicationUrl.MEDIATHEQUE, method = RequestMethod.GET)
    public String maMediatheque() {
        return PageMapping.MA_MEDIATHEQUE;
    }

    @RequestMapping(path = ApplicationUrl.MEDIATHEQUE_LISTE, method = RequestMethod.GET)
    @ResponseBody
    public List<Film> mediathequeListe(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        return filmService.findAllByIdUtilisateur(idUtilisateur);
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM_MEDIATHEQUE + "/{idFilm}", method = RequestMethod.POST)
    public String ajouterFilmAMaMediatheque(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes, @PathVariable(value = "idFilm") Long idFilm) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        try {
            utilisateurFilmService.ajouterFilmAMediatheque(idUtilisateur, idFilm);
            redirectAttributes.addFlashAttribute("success", "Votre film a bien été ajouté à votre médiathèque.");
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.FILM + "/" + idFilm;
        }
        return REDIRECT + ApplicationUrl.FILM + "/" + idFilm;
    }
}
