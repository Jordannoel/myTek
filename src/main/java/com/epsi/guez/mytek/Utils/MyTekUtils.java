package com.epsi.guez.mytek.Utils;

import com.epsi.guez.mytek.exception.MyTekException;
import com.epsi.guez.mytek.service.UtilisateurGroupeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Properties;

public class MyTekUtils {

    @Autowired
    private UtilisateurGroupeService utilisateurGroupeService;

    public static String sha256(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(motDePasse.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getProperty(String property) {
        String valeur = "";
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/myTek.properties");
            properties.load(input);
            valeur = properties.getProperty(property);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return valeur;
    }
}
