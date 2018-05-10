package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.Utils.MyTekUtils;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Utilisateur;
import com.epsi.guez.mytek.service.ConnexionService;
import com.epsi.guez.mytek.service.UtilisateurService;
import org.springframework.stereotype.Service;

@Service
public class ConnexionServiceImpl implements ConnexionService {

    private UtilisateurService utilisateurService;

    public ConnexionServiceImpl(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur connecterUtilisateur(String email, String motDePasse) throws FormInvalideException {

        FormInvalideException ex = new FormInvalideException();

        if (email != null && !email.equals("")) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                ex.addMessage("email", "Merci de saisir une adresse e-mail valide.");
            }
        } else {
            ex.addMessage("email", "Merci de saisir votre adresse e-mail.");
        }
        if (motDePasse != null && !motDePasse.equals("")) {
            if (!motDePasseCorrect(email, motDePasse)) {
                ex.addMessage("motDePasse", "Le mot de passe n'est pas correct.");
            }
        } else {
            ex.addMessage("motDePasse", "Merci de saisir votre mot de passe.");
        }
        if (ex.mustBeThrown()) {
            throw ex;
        }

        return utilisateurService.findOneByEmail(email);
    }

    private boolean motDePasseCorrect(String email, String motDePasse) {
        return MyTekUtils.sha256(motDePasse).equals(utilisateurService.findMotDePasseByEmail(email));
    }
}