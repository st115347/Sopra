package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören Schmidt on 07.07.2016.
 */
@Entity
public class Activity {

    @Id
    @GeneratedValue
    Integer activityId;

    Integer type;
    Integer time;

    String category;
    String variable;
    String variable2;
    String infotext;
    String user;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable) {
        this.variable2 = variable;
    }

    public Integer getType() {
        return type;
    }

    //type 0 = Lerngruppe beigetreten
    //type 1 = User-Infos geändert
    //type 2 = Beitrag verfasst
    //type 3 = Lerngruppe gelöscht
    //type 4 = Neue Frage erstellt
    //type 5 = Neues Quiz erstellt
    //type 6 = Punktzahl in Quiz
    public void setType(Integer type) {
         this.type = type;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(Integer type) {
        switch (type) {
            case 0:
                this.category = "Lerngruppenbeitritt";
                break;
            case 1:
                this.category = "Aktualisierung";
                break;
            case 2:
                this.category = "Neuer Beitrag";
                break;
            case 3:
                this.category = "Lerngruppe gelöscht";
                break;
            case 4:
                this.category = "Neue Frage";
                break;
            case 5:
                this.category = "Neues Quiz";
                break;
            case 6:
                this.category = "Quiz abgeschlossen";
                break;

        }
    }

    public String getInfotext() {
        return infotext;
    }

    public void setInfotext(Integer type) {
        switch (type){
            case 0: this.infotext = "Sie sind der Lerngruppe "+variable+" beigetreten.";
                break;
            case 1: this.infotext = "Sie haben Ihre Profilinformationen aktualisiert";
                break;
            case 2: this.infotext = "Sie haben einen Beitrag mit den Titel "+variable+" verfasst.";
                break;
            case 3: this.infotext = "Sie haben die Lerngruppe "+variable+" gelöscht.";
                break;
            case 4: this.infotext = "Sie haben eine neue Frage zum Thema "+variable+" erstellt.";
                break;
            case 5: this.infotext = "Sie haben ein neues Quiz mit dem Titel "+variable+" erstellt.";
                break;
            case 6: this.infotext = "Sie haben das Quiz "+variable+" mit "+variable2+" Punkten abgeschlossen.";
                break;


        }

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    //Abschließendes generieren der Activity
    public void generateActivity(){
        setCategory(getType());
        setInfotext(getType());

    }






}