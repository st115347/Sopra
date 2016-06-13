package de.uni.hohenheim.sopra.projekt.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bumiller on 06.06.2016.
 */
public interface SopraUserRepository extends JpaRepository<SopraUser, Integer> {

  SopraUser findByUsername (String username);

}
