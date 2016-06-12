package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
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
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    //Zur Referenz auf die Richtige Lerngruppe
    @Column(name = "lerngruppe")
    private Integer groupId;

    @OneToMany
    List<Antwort_Beitrag> answers = new ArrayList<Antwort_Beitrag>();

    int countAnswers=0;


//    private Set<Datei> datei = new HashSet<Datei>(0);
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projekt")
//    public Set<Datei> getDatei() {
//        return this.datei;
//    }
//
//    public void setDatei(Set<Datei> datei) {
//        this.datei = datei;
//    }

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

    public void removeAnswer(){

    }

    public void setCountAnswers(int x){

    }
    public int getCountAnswers(){
        return answers.size();
    }
}
