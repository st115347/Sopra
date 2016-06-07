package de.uni.hohenheim.sopra.projekt;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.ManagedBean;
>>>>>>> master
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
@Service
=======





@Service


>>>>>>> master
/**
 * Created by hilaltaylan on 05.06.16.
 */
public class BeitragBean implements Serializable{

    private String beitragName;
    private String neuerBeitragName;
    private String neuerBeitragId;
    private Beitrag neuerBeitrag = new Beitrag();


    @Autowired
    private LoginBean loginBean;



    @Bean
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    @Bean
    public String getNeuerBeitragName() {
        return neuerBeitragName;
    }

    @Bean
    public void setNeuerBeitragName(String neuerBeitragName) {
        this.neuerBeitragName = neuerBeitragName;
    }

    @Bean
    public String getNeuerBeitragId() {
        return neuerBeitragId;
    }

    @Bean
    public void setNeuerBeitragId(String neuesProjektId) {
        this.neuerBeitragId = neuesProjektId;
    }

<<<<<<< HEAD
    @Autowired
    private LoginBean loginBean;

=======

    @Bean
>>>>>>> master
    public String getBeitragName() {
        return beitragName;
    }

    @Bean
    public void setBeitragname(String BeitragName) {
        this.beitragName = beitragName;
    }

    @Bean
    public LoginBean getLoginBean() {
        return loginBean;
    }




    @Bean
    public List<String> schreibeBeitragListe(List<BeitragBean> beitragBeanListe) {
        ArrayList<String> projektNamensListe = new ArrayList<>();
        for (int i = 0; i < beitragBeanListe.size(); i++) {
            String beitragName = beitragBeanListe.get(i).getBeitragName();
            projektNamensListe.add(beitragName);
        }
        return projektNamensListe;

    }
    @Bean
    public void neuerBeitragAnlegen() {
        String id = neuerBeitrag.getId();
        String name = neuerBeitrag.getName();
        try {
            entityManager.getTransaction().begin();
            neuerBeitrag.setId(id);
            neuerBeitrag.setName(name);
            neuerBeitrag = entityManager.merge(neuerBeitrag);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();

        }
    }
    @Bean
    public List<String> getBeitragListe() {
        String email = loginBean.getEmail();
        Query q;

        q = entityManager.createQuery("SELECT p FROM Beitrag p");

        @SuppressWarnings("unchecked")
        List<BeitragBean> beitragBeanListe = q.getResultList();
        return schreibeBeitragListe(beitragBeanListe);

    }


    @Bean
    public BeitragBean printBeitrag() {
        String beitragname = beitragName;
        Query q = entityManager.createQuery(
                "SELECT p FROM Beitrag p WHERE p.name =:beitragname")
                .setParameter("beitragname", beitragname);
        return (BeitragBean) q.getSingleResult();
    }

    @Bean
    public Beitrag getneuerBeitrag() {
        return neuerBeitrag;
    }

    @Bean
    public void setneuerBeitrag(BeitragBean neuerBeitragBean) {
        this.neuerBeitrag = neuerBeitrag;
    }



    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("EMF");
    private EntityManager entityManager = emf.createEntityManager();



}

