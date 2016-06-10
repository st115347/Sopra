package de.uni.hohenheim.sopra.projekt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Sören on 06.06.2016.
 */
public interface LearningGroupRepository extends JpaRepository<LearningGroup, Integer> {

    List<LearningGroup> findByName(String name) ;


}