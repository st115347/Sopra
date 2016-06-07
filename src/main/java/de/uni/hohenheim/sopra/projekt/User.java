package de.uni.hohenheim.sopra.projekt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meltem on 02.06.2016.
 */
@Entity
public class User implements Serializable{


    private static final long serialVersionUID = 3039728383534501353L;


    @Id
    @Column(name = "email")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String email;

    @Column(name = "passwort")
    private String passwort;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @Column(name = "geburtsdatum")
    private String geburtsdatum;

    @Column(name = "strasse")
    private String strasse;

    @Column(name = "wohnort")
    private String wohnort;

    @Column(name = "plz")
    private String plz;


    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getWohnort() {
        return wohnort;
    }

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

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

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }





}

