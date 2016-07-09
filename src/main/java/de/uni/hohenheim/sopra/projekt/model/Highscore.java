package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabea on 07.07.16.
 */

@Entity
public class Highscore {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany
    private List<Htupel> htupels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Htupel> getHtupels() {
        return htupels;
    }

    public void setHtupels(List<Htupel> htupels) {
        this.htupels = htupels;
    }
}
