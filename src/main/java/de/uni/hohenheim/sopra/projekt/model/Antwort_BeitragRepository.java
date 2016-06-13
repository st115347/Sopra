package de.uni.hohenheim.sopra.projekt.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tabea on 12.06.16.
 * Interface is used to easily store and find answers
 */
public interface Antwort_BeitragRepository extends JpaRepository<Antwort_Beitrag, Integer> {
}
