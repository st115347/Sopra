package de.uni.hohenheim.sopra.projekt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by hilaltaylan on 06.06.16.
 */
@Entity
public class Beitrag implements Serializable{

    private static final long serialVersionUID = -8542604006878396929L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;



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

}
