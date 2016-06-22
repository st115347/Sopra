package de.uni.hohenheim.sopra.projekt.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören on 10.06.16.
 */
@Entity
public class Beitrag implements Serializable{

    private static final long serialVersionUID = -8542604006878396929L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    /*
    Changed ID type to Integer from String
     */
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "name")
    @Size(min=2, max=50, message="Topic muss zwischen 2 und 50 Zeichen lang sein")
    private String name;
    @Size(min=1, max=9999, message="Die Nachricht darf nicht leer sein oder länger als 9999 Zeichen")
    @Column(name = "text",length = 10000)
    private String text;

    //Zur Referenz auf die Richtige Lerngruppe
    @Column(name = "lerngruppe")
    private Integer groupId;

    /*
    List holding all answers to this beitrag.
     */
    @OneToMany
    List<Antwort_Beitrag> answers = new ArrayList<Antwort_Beitrag>();

    int countAnswers=0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setAnswers(Antwort_Beitrag b){
        answers.add(b);
    }

    public List<Antwort_Beitrag> getAnswers(){
        return answers;
    }

    //not yet used. maybe never neccesary
    public void removeAnswer(){

    }

    public void setCountAnswers(int x){

    }
    public int getCountAnswers(){
        return answers.size();
    }
}
