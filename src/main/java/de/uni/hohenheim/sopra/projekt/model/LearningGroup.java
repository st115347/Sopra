package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min=1, max=40, message = "Der Name muss zwischen 2-40 Zeichen lang sein")
    String name;
    Boolean visibility;
    int membercount;
    String owner;
    String password="";


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




    /**
     * Method adds Users to group only if there are free spots.
     * Also IMPORTANT!! First user to be added is also OWNER of this group!
     * @param user
     * @return
     */
    public boolean addUser(SopraUser user){
        if (sopraUsers.size()==0){
            owner = user.getVorname() + " " + user.getNachname();
        }
        if (sopraUsers.size() >=10){
            return false;
        }
        sopraUsers.add(user);
        membercount++;
        return true;
    }

    public void removeUser(SopraUser user){
        sopraUsers.remove(user);
        membercount--;
    }


    public int getMembercount(){
        return membercount;
    }


    public String getOwner(){
        return owner;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean comparePassword(Password password){
        if(this.password.equals(password.getPw())){
            return true;
        }
        return false;
    }

    public String getPassword(){
        return password;
    }





}