package com.epsi.guez.mytek.controller;

import com.epsi.guez.mytek.config.ApplicationUrl;
import com.epsi.guez.mytek.config.PageMapping;
import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.model.Groupe;
import com.epsi.guez.mytek.model.InscriptionGroupe;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    private UtilisateurGroupeService utilisateurGroupeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return PageMapping.INDEX;
    }

    @RequestMapping(value = ApplicationUrl.INDEX, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return PageMapping.INDEX;
    }

    @RequestMapping(value = ApplicationUrl.INSCRIPTION, method = RequestMethod.GET)
    public String inscription(ModelMap modelMap) {
        List<Groupe> groupes = groupeService.findAll();
        modelMap.put("groupes", groupes);
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
            List<Long> groupeIds = utilisateurGroupeService.findAllGroupesIdByAdminId(utilisateur.getId());
            HttpSession session = req.getSession();
            session.setAttribute("prenom", utilisateur.getPrenom());
            session.setAttribute("nom", utilisateur.getNom());
            session.setAttribute("email", utilisateur.getEmail());
            session.setAttribute("id", utilisateur.getId());
            session.setAttribute("groupes", groupeIds);
            return REDIRECT + ApplicationUrl.INSCRIPTION_VALIDEE;
        } catch (MyTekException e) {
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
        return PageMapping.CONNEXION;
    }

    @RequestMapping(value = ApplicationUrl.CONNEXION, method = RequestMethod.POST)
    public String connexionPost(HttpServletRequest req, ModelMap modelMap) {
        String email = req.getParameter("email");
        String motDePasse = req.getParameter("motDePasse");
        try {
            Utilisateur utilisateur = connexionService.connecterUtilisateur(email, motDePasse);
            List<Long> groupeIds = utilisateurGroupeService.findAllGroupesIdByAdminId(utilisateur.getId());
            HttpSession session = req.getSession();
            session.setAttribute("prenom", utilisateur.getPrenom());
            session.setAttribute("nom", utilisateur.getNom());
            session.setAttribute("email", utilisateur.getEmail());
            session.setAttribute("id", utilisateur.getId());
            session.setAttribute("groupes", groupeIds);
            return REDIRECT + ApplicationUrl.INDEX;
        } catch (MyTekException e) {
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
        List<Groupe> groupes = groupeService.findAll();
        modelMap.put("groupes", groupes);
        return PageMapping.CREER_GROUPE;
    }


    @RequestMapping(value = ApplicationUrl.CREER_GROUPE, method = RequestMethod.POST)
    public String creerGroupePost(HttpServletRequest req, ModelMap modelMap) {
        String nomGroupe = req.getParameter("nomGroupe");
        String urlImage = req.getParameter("urlImage");
        boolean approbation = Boolean.valueOf(req.getParameter("approbation"));
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        Utilisateur utilisateur = utilisateurService.findOneById(id);
        try {
            InscriptionGroupe inscriptionGroupe = inscriptionService.inscrireGroupe(nomGroupe, urlImage, approbation, utilisateur);
            session.setAttribute("dernierGroupeCree", inscriptionGroupe.getNom());
            session.setAttribute("urlDernierGroupeCree", inscriptionGroupe.getUrlImage());

            return REDIRECT + ApplicationUrl.CREATION_GROUPE_VALIDEE;
        } catch (MyTekException e) {
            List<Groupe> groupes = groupeService.findAll();
            modelMap.put("groupes", groupes);
            modelMap.put("errors", e.getMessages());
            return PageMapping.CREER_GROUPE;
        }
    }

    @RequestMapping(value = ApplicationUrl.CREATION_GROUPE_VALIDEE, method = RequestMethod.GET)
    public String creationGroupeValidee(HttpServletRequest req, ModelMap modelMap) {
        HttpSession session = req.getSession();
        modelMap.put("nomGroupe", session.getAttribute("dernierGroupeCree"));
        modelMap.put("urlImage", session.getAttribute("urlDernierGroupeCree"));
        return PageMapping.CREATION_GROUPE_VALIDEE;
    }

    @RequestMapping(value = ApplicationUrl.REJOINDRE_GROUPE, method = RequestMethod.GET)
    public String rejoindreGroupe(ModelMap modelMap) {
        List<Groupe> groupes = groupeService.findAll();
        modelMap.put("groupes", groupes);
        return PageMapping.REJOINDRE_GROUPE;
    }

    @RequestMapping(value = ApplicationUrl.REJOINDRE_GROUPE, method = RequestMethod.POST)
    public String rejoindreGroupePost(ModelMap modelMap, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        Long idGroupe = Long.valueOf(req.getParameter("groupe"));
        try {
            utilisateurService.setGroupeUtilisateur(idUtilisateur, idGroupe);
            redirectAttributes.addFlashAttribute("success", "Vous êtes maintenant affecté au groupe " + groupeService.findOneById(idGroupe).getNomGroupe() + ".");
            return REDIRECT + ApplicationUrl.REJOINDRE_GROUPE;
        } catch (MyTekException ex) {
            List<Groupe> groupes = groupeService.findAll();
            modelMap.put("groupes", groupes);
            modelMap.put("errors", ex.getMessages());
            return PageMapping.REJOINDRE_GROUPE;
        }
    }

    @RequestMapping(value = ApplicationUrl.VOIR_GROUPES, method = RequestMethod.GET)
    public String voirGroupes(ModelMap modelMap, HttpServletRequest req) {
        List<Groupe> groupes = groupeService.findAll();
        modelMap.put("groupes", groupes);
        return PageMapping.VOIR_GROUPES;
    }

    @RequestMapping(value = ApplicationUrl.GROUPE + "/{idGroupe}", method = RequestMethod.GET)
    public String groupe(ModelMap modelMap, HttpServletRequest req, @PathVariable(value = "idGroupe") Long idGroupe, RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        Long idUtilisateur = (Long) session.getAttribute("id");
        try {
            utilisateurService.utilisateurConnecte(idUtilisateur);
            utilisateurService.utilisateurPeutVoirGroupe(idUtilisateur, idGroupe);

            Groupe groupe = groupeService.findOneById(idGroupe);
            List<Long> adminIds = utilisateurGroupeService.findAllAdminIdByGroupeId(idGroupe);
            List<Long> membresIds = utilisateurGroupeService.findAllMembresIdByGroupeId(idGroupe);
            List<Utilisateur> admins = new ArrayList<>();
            List<Utilisateur> membres = new ArrayList<>();
            if (adminIds.size() != 0) {
                admins = utilisateurService.findAllByIdIn(adminIds);
            }
            if (membresIds.size() != 0) {
                membres = utilisateurService.findAllByIdIn(membresIds);
            }
            modelMap.put("groupe", groupe);
            modelMap.put("admins", admins);
            modelMap.put("membres", membres);
            return PageMapping.GROUPE;
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.VOIR_GROUPES;
        }
    }

    @RequestMapping(value = ApplicationUrl.VOIR_COMPTE, method = RequestMethod.GET)
    public String voirCompte(HttpServletRequest req, ModelMap modelMap) {
        HttpSession session = req.getSession();
        List<Long> groupeIds = utilisateurGroupeService.findAllGroupesIdByUtilisateurId((Long) session.getAttribute("id"));
        if (groupeIds.size() != 0) {
            List<Groupe> groupes = groupeService.findAllByIdIn(groupeIds);
            modelMap.put("groupes", groupes);
        }
        return PageMapping.VOIR_COMPTE;
    }

    @RequestMapping(value = ApplicationUrl.RENDRE_ADMINISTRATEUR + "/{idMembre}/{idGroupe}", method = RequestMethod.POST)
    public String rendreAdministrateur(HttpServletRequest req, @PathVariable(value = "idMembre") Long idMembre,
                                       @PathVariable(value = "idGroupe") Long idGroupe, RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        Long idAdmin = (Long) session.getAttribute("id");
        try {
            utilisateurGroupeService.rendreMembreAdministrateur(idAdmin, idMembre, idGroupe);
            return REDIRECT + ApplicationUrl.GROUPE + "/" + idGroupe;
        } catch (MyTekException ex) {
            redirectAttributes.addFlashAttribute("errors", ex.getMessages());
            return REDIRECT + ApplicationUrl.GROUPE + "/" + idGroupe;
        }
    }
}




