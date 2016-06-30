package de.uni.hohenheim.sopra.projekt.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören on 10.06.2016.
 */
@Entity
public class SopraUser implements Serializable{


  private static final long serialVersionUID = 3039728383534501353L;


  @Id
  @Size(min=1, message="Bitte geben Sie eine Email Adresse ein")
  @Column (name = "username")
  private String username;

  @Column(name = "email")
  private String email;


  @Size(min=1, message="Bitte wählen Sie ein Passwort")
  @Column(name = "passwort")
  private String passwort;

  @Size(min=1, message="Bitte geben Sie ihren Vornamen an")
  @Column(name = "vorname")
  private String vorname;

  @Size(min=1, message="Bitte geben Sie ihren Nachnamen an")
  @Column(name = "nachname")
  private String nachname;

  @Column(name = "geburtsdatum")
  private String geburtsdatum;

  @Column(name = "hochschule")
  private String hochschule;

  @Column(name = "studiengang")
  private String studiengang;

  @Column(name = "fachsemester")
  private Integer fachsemester;


  public String getGeburtsdatum() {
    return geburtsdatum;
  }

  public void setGeburtsdatum(String geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
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

  public String getHochschule() {
    return hochschule;
  }

  public void setHochschule(String hochschule) {
    this.hochschule = hochschule;
  }

  public String getStudiengang() {
    return studiengang;
  }

  public void setStudiengang(String studiengang) {
    this.studiengang = studiengang;
  }

  public Integer getFachsemester() {
    return fachsemester;
  }

  public void setFachsemester(Integer fachsemester) {
    this.fachsemester = fachsemester;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


}

