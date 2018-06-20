package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.dao.UtilisateurFilmDao;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.service.UtilisateurFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MediathequeController {

    private static final String REDIRECT = "redirect:";
    @Autowired
    private UtilisateurFilmDao utilisateurFilmDao;

    @Autowired
    private UtilisateurFilmService utilisateurFilmService;

    @RequestMapping(value = ApplicationUrl.MEDIATHEQUE, method = RequestMethod.GET)
    public String maMediatheque(HttpServletRequest req, ModelMap modelMap) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
//        List<Film> mediatheque = filmDao.
//        modelMap.put("mediatheque", mediatheque);
        return PageMapping.CREER_GROUPE;
    }

    @RequestMapping(value = ApplicationUrl.AJOUTER_FILM_MEDIATHEQUE + "/{idFilm}", method = RequestMethod.POST)
    public String ajouterFilmAMaMediatheque(HttpServletRequest req, ModelMap modelMap, RedirectAttributes redirectAttributes, @PathVariable(value = "idFilm") Long idFilm) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        try {
            utilisateurFilmService.ajouterFilmAMediatheque(idUtilisateur, idFilm);
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.VOIR_FILMS;
        }
        return REDIRECT + ApplicationUrl.VOIR_FILMS;
    }
}
