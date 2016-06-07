package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import org.springframework.context.annotation.Bean;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service

/**
 * Created by hilaltaylan on 06.06.16.
 */
public class UserBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String userEmail;
    private String userVorname;
    private String userNachname;
    private String userGebDatum;
    private String userStrasse;
    private String userWohnort;
    private String userPlz;
    private String userPw;



    @Autowired
    private LoginBean loginBean;

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @Autowired
    private BeitragBean beitragBean;


    public void setBeitragBean(BeitragBean beitragBean) {
        this.beitragBean = beitragBean;
    }

    @Bean
    public String getUserGebDatum() {
        return userGebDatum;
    }

    @Bean
    public void setUserGebDatum(String userGebDatum) {
        this.userGebDatum = userGebDatum;
    }

    @Bean
    public String getUserStrasse() {
        return userStrasse;
    }

    @Bean
    public void setUserStrasse(String userStrasse) {
        this.userStrasse = userStrasse;
    }

    @Bean
    public String getUserWohnort() {
        return userWohnort;
    }

    @Bean
    public void setUserWohnort(String userWohnort) {
        this.userWohnort = userWohnort;
    }

    @Bean
    public String getUserPlz() {
        return userPlz;
    }

    @Bean
    public void setUserPlz(String userPlz) {
        this.userPlz = userPlz;
    }

    @Bean
    public LoginBean getLoginBean() {
        return loginBean;
    }

    @Bean
    public BeitragBean getBeitragBean() {
        return beitragBean;
    }

    @Bean
    public String getUserVorname() {
        return userVorname;
    }

    @Bean
    public void setUserVorname(String userVorname) {
        this.userVorname = userVorname;
    }

    @Bean
    public String getUserNachname() {
        return userNachname;
    }

    @Bean
    public void setUserNachname(String userNachname) {
        this.userNachname = userNachname;
    }

    @Bean
    public String getUserEmail() {
        return userEmail;
    }

    @Bean
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



    public List<String> schreibeUserListe(List<User> userListe) {
        ArrayList<String> emailListe = new ArrayList<>();
        for (int i = 0; i < userListe.size(); i++) {
            String email = userListe.get(i).getEmail();
            emailListe.add(email);
        }
        return emailListe;

    }

    @Bean
    public List<String> getUserListe() {
        Query q = entityManager.createQuery("SELECT n FROM User n");
        @SuppressWarnings("unchecked")
        List<User> userListe = q.getResultList();
        return schreibeUserListe(userListe);

    }





    @Bean
    public User ladeUser(String email) {
        Query q = entityManager.createQuery(
                "SELECT n from User n where n.email =:email").setParameter(
                "email", email);
        return (User) q.getSingleResult();
    }

    @Bean
    public String getUserPw() {
        return userPw;
    }

    @Bean
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }











    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("EMF");
    private EntityManager entityManager = emf.createEntityManager();


}
