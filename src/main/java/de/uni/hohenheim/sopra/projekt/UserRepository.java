package de.hohenheim.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Meltem on 02.06.2016.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}