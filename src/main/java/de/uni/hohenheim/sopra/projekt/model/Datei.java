package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;

@Entity
/**
 * Created by hilaltaylan on 06.06.16.
 */
public class Datei {

    @Id
    @GeneratedValue
    int i;
    @Column(name = "datei")
    private String fileContent;


    private Beitrag beitrag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beitrag_id", nullable = false)
    public Beitrag getBeitrag() {
        return this.beitrag;
    }

    public void setProjekt(Beitrag beitrag) {
        this.beitrag = beitrag;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Datei(String fileContent){
    }

    public Datei(){

    }
}
