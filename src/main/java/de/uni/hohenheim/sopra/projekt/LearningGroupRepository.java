package de.uni.hohenheim.sopra.projekt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by Sören on 06.06.2016.
 */
public interface LearningGroupRepository extends JpaRepository<LearningGroup, Integer> {

    List<LearningGroup> findByName(String name) ;


}