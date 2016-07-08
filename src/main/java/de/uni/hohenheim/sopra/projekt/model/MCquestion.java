package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by Sören Schmidt on 29.06.2016.
 */

@Entity
public class MCquestion {



    @Id
    @GeneratedValue
    @Column(name = "id")
    /*
    Changed ID type to Integer from String
     */
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "authorID")
    private String authorID;

    //Zur Zuordnung der Frage zu einem konkreten Quiz
    @Column(name = "quiz")
    private Integer quiz;

    //Zuordnung zu konkretem Thema
    @Column(name = "thema")
    private String thema;

    //konkrete Fragestellung
    @Column(name = "frage",length = 10000)
    private String frage;

    //Antwortmöglichkeit 1
    @Column(name = "antwort1")
    private String antwort1;

    //0 wenn Antwortmöglichkeit nicht benutzt wird
    //1 wenn Antwortmöglichkeit richtig
    //2 wenn Antwortmöglichkeit falsch
    @Column(name ="antwort1_lsg")
    private Integer antwort1_lsg;

    @Column(name = "antwort2")
    private String antwort2;

    @Column(name ="antwort2_lsg")
    private Integer antwort2_lsg;

    @Column(name = "antwort3")
    private String antwort3;

    @Column(name ="antwort3_lsg")
    private Integer antwort3_lsg;

    @Column(name = "antwort4")
    private String antwort4;

    @Column(name ="antwort4_lsg")
    private Integer antwort4_lsg;


    //Zur Referenz auf die Richtige Lerngruppe
    @Column(name = "lerngruppe")
    private Integer groupId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public String getAntwort1() {
        return antwort1;
    }

    public void setAntwort1(String antwort1) {
        this.antwort1 = antwort1;
    }

    public Integer getAntwort1_lsg() {
        return antwort1_lsg;
    }

    public void setAntwort1_lsg(Integer antwort1_lsg) {
        this.antwort1_lsg = antwort1_lsg;
    }

    public String getAntwort2() {
        return antwort2;
    }

    public void setAntwort2(String antwort2) {
        this.antwort2 = antwort2;
    }

    public Integer getAntwort2_lsg() {
        return antwort2_lsg;
    }

    public void setAntwort2_lsg(Integer antwort2_lsg) {
        this.antwort2_lsg = antwort2_lsg;
    }
    public String getAntwort3() {
        return antwort3;
    }

    public void setAntwort3(String antwort3) {
        this.antwort3 = antwort3;
    }

    public Integer getAntwort3_lsg() {
        return antwort3_lsg;
    }

    public void setAntwort3_lsg(Integer antwort3_lsg) {
        this.antwort3_lsg = antwort3_lsg;
    }
    public String getAntwort4() {
        return antwort4;
    }

    public void setAntwort4(String antwort4) {
        this.antwort4 = antwort4;
    }

    public Integer getAntwort4_lsg() {
        return antwort4_lsg;
    }

    public void setAntwort4_lsg(Integer antwort4_lsg) {
        this.antwort4_lsg = antwort4_lsg;
    }
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setAuthorID(String id){authorID = id;}
    public String getAuthorID(){return authorID;}


}









