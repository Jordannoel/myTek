package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.Utils.MyTekUtils;
import com.epsi.guez.mytek.dao.GroupeDao;
import com.epsi.guez.mytek.dao.UtilisateurDao;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Groupe;
import com.epsi.guez.mytek.model.InscriptionGroupe;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.InscriptionService;
import org.springframework.stereotype.Service;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    private UtilisateurDao utilisateurDao;

    private GroupeDao groupeDao;

    public InscriptionServiceImpl(UtilisateurDao utilisateurDao, GroupeDao groupeDao) {
        this.utilisateurDao = utilisateurDao;
        this.groupeDao = groupeDao;
    }

    public void inscrireUtilisateur(String prenom, String nom, String email, String motDePasse, String confirmationMotDePasse,
                                    Long idGroupe, boolean approbation) throws FormInvalideException {

        FormInvalideException ex = new FormInvalideException();

        if (prenom == null || prenom.equals("")) {
            ex.addMessage("prenom", "Veuillez saisir votre prénom.");
        }
        if (nom == null || nom.equals("")) {
            ex.addMessage("nom", "Veuillez saisir votre nom.");
        }
        if (email == null || email.equals("")) {
            ex.addMessage("email", "Merci de saisir votre adresse e-mail.");
        } else if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            ex.addMessage("email", "Merci de saisir une adresse mail valide.");
        }
        if (emailDejaExistant(email)) {
            ex.addMessage("email", "Un compte est déjà associé à cette adresse e-mail");
        }
        if (motDePasse == null || motDePasse.length() < 8) {
            ex.addMessage("motDePasse", "Le mot de passe doit contenir au moins 8 caractères.");
        }
        if (motDePasse != null && !motDePasse.equals(confirmationMotDePasse)) {
            ex.addMessage("confirmationMotDePasse", "Les deux mots de passe ne sont pas identiques.");
        }
        if (!approbation) {
            ex.addMessage("approbation", "Vous devez accepter les conditions.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }

        if (idGroupe == 0) {
            utilisateurDao.save(new Utilisateur(prenom, nom, email, MyTekUtils.sha256(motDePasse)));
        } else {
            Groupe groupe = groupeDao.findOneById(idGroupe);
            utilisateurDao.save(new Utilisateur(prenom, nom, email, MyTekUtils.sha256(motDePasse), groupe));
        }
    }

    @Override
    public InscriptionGroupe inscrireGroupe(String nomGroupe, String urlImage, boolean approbation, Utilisateur createur) throws FormInvalideException {
        FormInvalideException ex = new FormInvalideException();
        if (nomGroupe == null || nomGroupe.equals("")) {
            ex.addMessage("nomGroupe", "Veuillez saisir un nom de groupe.");
        } else {
            Groupe groupeExistant = groupeDao.findOneByNomGroupe(nomGroupe);
            if (groupeExistant != null) {
                ex.addMessage("nomGroupe", "Le nom de votre groupe existe déjà");
            }
        }
        if (!approbation) {
            ex.addMessage("approbation", "Vous devez accepter les conditions.");
        }
        if (createur == null) {
            ex.addMessage("utilisateur", "Vous devez être connecté pour pouvoir créer un groupe.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }
        if (urlImage == null || urlImage.equals("")) {
            urlImage = MyTekUtils.getProperty("aucuneImage");
        }

        groupeDao.save(new Groupe(nomGroupe, urlImage));
        return new InscriptionGroupe(nomGroupe, urlImage);
    }

    private boolean emailDejaExistant(String email) {
        return utilisateurDao.countByEmail(email) > 0;
    }
}
