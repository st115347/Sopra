package de.uni.hohenheim.sopra.projekt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.ManagedBean;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

@Service
/**
 * Created by hilaltaylan on 05.06.16.
 */

public class LoginBean implements Serializable{




        private String email;
        private String passwort;
        private String name;

        @Bean
        public String getName(){return name;}

        @Bean
        public void setName(String name){this.name=name;}

        @Bean
        public String getEmail() {
            return email;
        }

        @Bean
        public void setEmail(String email) {
            this.email = email;
        }

        @Bean
        public String getPasswort() {
            return passwort;
        }

        @Bean
        public void setPasswort(String passwort) {
            this.passwort = passwort;
        }



        @Bean
        public String authentifikation() {
            User user = getUser(email);
            if (user != null) {
                if (user.getPasswort().equals(passwort)) {

                    name = user.getVorname().substring(0, 1).toUpperCase()
                            + user.getVorname().substring(1).toLowerCase() + " "
                            + user.getNachname().substring(0, 1).toUpperCase()
                            + user.getNachname().substring(1).toLowerCase();


                }
            }

            return "loginFail?faces-redirect=true";

        }

        private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";





    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("EMF");
    private EntityManager entityManager = emf.createEntityManager();

        @Bean
        public User getUser(String email) {
            User n = null;
            Query q = entityManager.createQuery(
                    "SELECT n FROM User n WHERE n.email =:email").setParameter(
                    "email", email);
            @SuppressWarnings("unchecked")
            List<User> l = q.getResultList();
            for (int i = 0; i < l.size(); i++) {
                n = l.get(i);
            }

            return n;

        }




    }


