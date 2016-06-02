package de.hohenheim.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meltem on 02.06.2016.
 */
@Entity
public class LearningGroup {

    @Id
    @GeneratedValue
    Integer groupId;

    String name;

    @ManyToMany
    @JoinTable(
            name="GROUPPARTICIPANTS",
            joinColumns=@JoinColumn(name="GROUP_ID"),
            inverseJoinColumns=@JoinColumn(name="USER_ID"))
    List<User> users = new ArrayList<>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}