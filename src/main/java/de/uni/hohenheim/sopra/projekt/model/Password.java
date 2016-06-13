package de.uni.hohenheim.sopra.projekt.model;

/**
 * Created by Tabea on 11.06.16.
 * This class is used to short term store passwords in objects.
 */
public class Password {
    String pw;
    public int id;

    public String getPw(){
        return pw;
    }

    public void setPw(String pw){
        this.pw = pw;
    }
}
