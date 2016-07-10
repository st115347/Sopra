package de.uni.hohenheim.sopra.projekt.model;

import de.uni.hohenheim.sopra.projekt.model.MCquestion;

import javax.persistence.*;

/**
 * Created by Tabea on 07.07.16.
 */
@Entity
public class Htupel implements Comparable<Htupel> {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="score")
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }



    @Override
    public int compareTo(Htupel o) {
        if(this.getScore() < o.getScore()){
            return 1;
        }
        if(this.getScore() > o.getScore()){
            return -1;
        }
        return 0;
    }
}
