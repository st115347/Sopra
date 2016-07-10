package de.uni.hohenheim.sopra.projekt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Tabea on 07.07.16.
 */

public interface HtupelRepository extends JpaRepository<Htupel, Integer> {


}
