package de.uni.hohenheim.sopra.projekt;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

/**
 * Created by hilaltaylan on 05.06.16.
 */
public class LoginBean {




        private String email;
        private String passwort;
        private String name;


        public String getName(){return name;}

        public void setName(String name){this.name=name;}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPasswort() {
            return passwort;
        }

        public void setPasswort(String passwort) {
            this.passwort = passwort;
        }




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


