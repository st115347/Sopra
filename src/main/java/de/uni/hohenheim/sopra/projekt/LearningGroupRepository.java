package de.hohenheim.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Meltem on 02.06.2016.
 */
public interface LearningGroupRepository extends JpaRepository<LearningGroup, Integer> {
}