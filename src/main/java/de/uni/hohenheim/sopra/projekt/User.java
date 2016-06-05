package de.uni.hohenheim.sopra.projekt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Meltem on 02.06.2016.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    Integer id;

    String name;
}