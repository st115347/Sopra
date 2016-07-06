package de.uni.hohenheim.sopra.projekt.model;

import org.hibernate.annotations.CollectionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Tabea on 03.07.16.
 */
@Entity
public class ListHelper {

    @Id
    @GeneratedValue
    Integer id;

    @Column
    private ArrayList<Boolean> list = new ArrayList<Boolean>();
    @Column
    private Boolean info;
    public Integer getID(){
        return id;
    }
    public void setID(Integer id){
        this.id = id;
    }
    public void setInfo(Boolean b){
        list.add(b);
    }
    public Boolean getInfo(){
        return info;
    }
    public ArrayList<Boolean> getList(){
        return list;
    }
    public void setList(ArrayList<Boolean> l){
        list.addAll(l);
    }
}

