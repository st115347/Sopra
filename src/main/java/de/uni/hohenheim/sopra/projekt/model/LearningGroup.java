package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören on 08.06.2016.
 */
@Entity
public class LearningGroup {

    @Id
    @GeneratedValue
    Integer groupId;

    String name;
    Boolean visibility;

    @ManyToMany
    @JoinTable(
            name="GROUPPARTICIPANTS",
            joinColumns=@JoinColumn(name="GROUP_ID"),
            inverseJoinColumns=@JoinColumn(name="USER_ID"))
    List<SopraUser> sopraUsers = new ArrayList<>();

    public Integer getId() {
        return groupId;
    }

    public void setId(Integer id) {
        this.groupId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SopraUser> getSopraUsers() {
        return sopraUsers;
    }

    public void setSopraUsers(List<SopraUser> sopraUsers) {
        this.sopraUsers = sopraUsers;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public boolean addUser(SopraUser user){
        if (sopraUsers.size() > 10){
            return false;
        }
        sopraUsers.add(user);
        return true;
    }

    public void removeUser(SopraUser user){
        sopraUsers.remove(user);
    }





}