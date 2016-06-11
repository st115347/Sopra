package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sören on 10.06.16.
 */
@Entity
public class Beitrag implements Serializable{

    private static final long serialVersionUID = -8542604006878396929L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "autor")
    private String autor;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    //Zur Referenz auf die Richtige Lerngruppe
    @Column(name = "lerngruppe")
    private Integer groupId;



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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
