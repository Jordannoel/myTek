package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Groupe;
import com.epsi.guez.mytek.model.InscriptionGroupe;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.ConnexionService;
import com.epsi.guez.mytek.service.GroupeService;
import com.epsi.guez.mytek.service.InscriptionService;
import com.epsi.guez.mytek.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdministrationController {

    private static final String REDIRECT = "redirect:";

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private ConnexionService connexionService;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return PageMapping.INDEX;
    }

    @RequestMapping(value = ApplicationUrl.INDEX, method = RequestMethod.GET)
    public String index() {
        return PageMapping.INDEX;
    }

    @RequestMapping(value = ApplicationUrl.INSCRIPTION, method = RequestMethod.GET)
    public String inscription(HttpServletRequest req, ModelMap modelMap) {
        List<Groupe> groupes = groupeService.findAll();
        modelMap.put("groupes", groupes);
        modelMap.put("errors", new HashMap<>());
        return PageMapping.INSCRIPTION;
    }

    @RequestMapping(value = ApplicationUrl.INSCRIPTION, method = RequestMethod.POST)
    public String inscriptionPost(HttpServletRequest req, ModelMap modelMap) {
        String prenom = req.getParameter("prenom");
        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("motDePasse");
        String confirmationMotDePasse = req.getParameter("confirmationMotDePasse");
        Long idGroupe = Long.valueOf(req.getParameter("groupe"));
        boolean approbation = Boolean.valueOf(req.getParameter("approbation"));
        try {
            inscriptionService.inscrireUtilisateur(prenom, nom, email, motDePasse, confirmationMotDePasse, idGroupe, approbation);
            Utilisateur utilisateur = connexionService.connecterUtilisateur(email, motDePasse);
            HttpSession session = req.getSession();
            session.setAttribute("prenom", utilisateur.getPrenom());
            session.setAttribute("nom", utilisateur.getNom());
            session.setAttribute("email", utilisateur.getEmail());
            session.setAttribute("id", utilisateur.getId());
            return REDIRECT + ApplicationUrl.INSCRIPTION_VALIDEE;
        } catch (FormInvalideException e) {
            List<Groupe> groupes = groupeService.findAll();
            modelMap.put("groupes", groupes);
            modelMap.put("errors", e.getMessages());
            return PageMapping.INSCRIPTION;
        }
    }

    @RequestMapping(value = ApplicationUrl.INSCRIPTION_VALIDEE, method = RequestMethod.GET)
    public String inscriptionValidee(HttpServletRequest req, ModelMap modelMap) {
        HttpSession session = req.getSession();
        modelMap.put("prenom", session.getAttribute("prenom"));
        modelMap.put("nom", session.getAttribute("nom"));
        modelMap.put("email", session.getAttribute("email"));
        return PageMapping.INSCRIPTION_VALIDEE;
    }

    @RequestMapping(value = ApplicationUrl.CONNEXION, method = RequestMethod.GET)
    public String connexion(ModelMap modelMap) {
        modelMap.put("errors", new HashMap<>());
        return PageMapping.CONNEXION;
    }

    @RequestMapping(value = ApplicationUrl.CONNEXION, method = RequestMethod.POST)
    public String connexionPost(HttpServletRequest req, ModelMap modelMap) {
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("motDePasse");
        try {
            Utilisateur utilisateur = connexionService.connecterUtilisateur(email, motDePasse);
            HttpSession session = req.getSession();
            session.setAttribute("prenom", utilisateur.getPrenom());
            session.setAttribute("nom", utilisateur.getNom());
            session.setAttribute("email", utilisateur.getEmail());
            session.setAttribute("id", utilisateur.getId());
            return REDIRECT + ApplicationUrl.INDEX;
        } catch (FormInvalideException e) {
            modelMap.put("errors", e.getMessages());
            return PageMapping.CONNEXION;
        }
    }

    @RequestMapping(value = ApplicationUrl.DECONNEXION, method = RequestMethod.POST)
    public String deconnexionPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return REDIRECT + ApplicationUrl.INDEX;
    }

    @RequestMapping(value = ApplicationUrl.CREER_GROUPE, method = RequestMethod.GET)
    public String creerGroupe(ModelMap modelMap) {
        modelMap.put("errors", new HashMap<>());
        return PageMapping.CREER_GROUPE;
    }


    @RequestMapping(value = ApplicationUrl.CREER_GROUPE, method = RequestMethod.POST)
    public String creerGroupePost(HttpServletRequest req, ModelMap modelMap) {
        String nomGroupe = req.getParameter("nomGroupe");
        String urlImage = req.getParameter("urlImage");
        boolean approbation = Boolean.valueOf(req.getParameter("approbation"));
        try {
            HttpSession session = req.getSession();
            Long id = (Long) session.getAttribute("id");
            Utilisateur utilisateur = utilisateurService.findOneById(id);
            InscriptionGroupe inscriptionGroupe = inscriptionService.inscrireGroupe(nomGroupe, urlImage, approbation, utilisateur);
            modelMap.put("nomGroupe", inscriptionGroupe.getNom());
            modelMap.put("urlImage", inscriptionGroupe.getUrlImage());
            modelMap.put("date", inscriptionGroupe.getDate());

            // sûrement mettre un session.setAttribute() ici

            return REDIRECT + ApplicationUrl.CREATION_GROUPE_VALIDEE;
        } catch (FormInvalideException e) {
            modelMap.put("errors", e.getMessages());
            return PageMapping.CREER_GROUPE;
        }
    }

    @RequestMapping(value = ApplicationUrl.CREATION_GROUPE_VALIDEE, method = RequestMethod.GET)
    public String creationGroupeValidee(HttpServletRequest req, ModelMap modelMap) {
        modelMap.put("errors", new HashMap<>());
        return PageMapping.CREATION_GROUPE_VALIDEE;
    }
}




