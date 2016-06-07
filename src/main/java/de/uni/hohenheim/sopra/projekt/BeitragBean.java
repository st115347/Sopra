package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Service
/**
 * Created by hilaltaylan on 05.06.16.
 */
public class BeitragBean {

    private String beitragName;

    private String neuerBeitragName;
    private String neuerBeitragId;
    private Beitrag neuerBeitrag = new Beitrag();

    public String getNeuerBeitragName() {
        return neuerBeitragName;
    }

    public void setNeuerBeitragName(String neuerBeitragName) {
        this.neuerBeitragName = neuerBeitragName;
    }

    public String getNeuerBeitragId() {
        return neuerBeitragId;
    }

    public void setNeuerBeitragId(String neuesProjektId) {
        this.neuerBeitragId = neuesProjektId;
    }

    @Autowired
    private LoginBean loginBean;

    public String getBeitragName() {
        return beitragName;
    }

    public void setBeitragname(String BeitragName) {
        this.beitragName = beitragName;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<String> schreibeBeitragListe(List<BeitragBean> beitragBeanListe) {
        ArrayList<String> projektNamensListe = new ArrayList<>();
        for (int i = 0; i < beitragBeanListe.size(); i++) {
            String beitragName = beitragBeanListe.get(i).getBeitragName();
            projektNamensListe.add(beitragName);
        }
        return projektNamensListe;

    }

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
    public List<String> getBeitragListe() {
        String email = loginBean.getEmail();
        Query q;

        q = entityManager.createQuery("SELECT p FROM Beitrag p");

        @SuppressWarnings("unchecked")
        List<BeitragBean> beitragBeanListe = q.getResultList();
        return schreibeBeitragListe(beitragBeanListe);

    }



    public BeitragBean printBeitrag() {
        String beitragname = beitragName;
        Query q = entityManager.createQuery(
                "SELECT p FROM Beitrag p WHERE p.name =:beitragname")
                .setParameter("beitragname", beitragname);
        return (BeitragBean) q.getSingleResult();
    }

    public Beitrag getneuerBeitrag() {
        return neuerBeitrag;
    }

    public void setneuerBeitrag(BeitragBean neuerBeitragBean) {
        this.neuerBeitrag = neuerBeitrag;
    }


    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("EMF");
    private EntityManager entityManager = emf.createEntityManager();



}

