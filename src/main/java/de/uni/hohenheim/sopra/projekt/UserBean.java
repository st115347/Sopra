package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service

/**
 * Created by hilaltaylan on 06.06.16.
 */
public class UserBean {


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

    @Autowired
    private BeitragBean beitragBean;


    public String getUserGebDatum() {
        return userGebDatum;
    }

    public void setUserGebDatum(String userGebDatum) {
        this.userGebDatum = userGebDatum;
    }

    public String getUserStrasse() {
        return userStrasse;
    }

    public void setUserStrasse(String userStrasse) {
        this.userStrasse = userStrasse;
    }

    public String getUserWohnort() {
        return userWohnort;
    }

    public void setUserWohnort(String userWohnort) {
        this.userWohnort = userWohnort;
    }

    public String getUserPlz() {
        return userPlz;
    }

    public void setUserPlz(String userPlz) {
        this.userPlz = userPlz;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getUserVorname() {
        return userVorname;
    }

    public void setUserVorname(String userVorname) {
        this.userVorname = userVorname;
    }

    public String getUserNachname() {
        return userNachname;
    }

    public void setUserNachname(String userNachname) {
        this.userNachname = userNachname;
    }







    public String getUserEmail() {
        return userEmail;
    }

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

    public List<String> getUserListe() {
        Query q = entityManager.createQuery("SELECT n FROM User n");
        @SuppressWarnings("unchecked")
        List<User> userListe = q.getResultList();
        return schreibeUserListe(userListe);

    }






    public User ladeUser(String email) {
        Query q = entityManager.createQuery(
                "SELECT n from User n where n.email =:email").setParameter(
                "email", email);
        return (User) q.getSingleResult();
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }











    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("EMF");
    private EntityManager entityManager = emf.createEntityManager();


}
