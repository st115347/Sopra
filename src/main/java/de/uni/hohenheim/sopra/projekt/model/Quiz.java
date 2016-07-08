package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tabea on 03.07.16.
 */
@Entity
public class Quiz implements Serializable{
    private static final long serialVersionUID = -8542604006878396929L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    /*
    Changed ID type to Integer from String
     */
    private Integer id;

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Column
    private Integer groupID;

    @Column(name = "author")
    private String author;

    @Column(name = "authorID")
    private String authorID;
    @Column(name = "finished")
    private Boolean finished;

    @Size(min=1, message="Bitte tragen Sie einen Namen ein")
    @Column (name = "name")
    private String name;

    @ManyToMany
    private List<MCquestion> questions = new ArrayList<MCquestion>();

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

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public List<MCquestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MCquestion> question) {
        this.questions = question;
    }
    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
