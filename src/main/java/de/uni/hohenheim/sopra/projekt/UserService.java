package de.uni.hohenheim.sopra.projekt;

import de.uni.hohenheim.sopra.projekt.model.SopraUser;
import de.uni.hohenheim.sopra.projekt.model.SopraUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by bumiller on 06.06.2016.
 */
@Service
public class UserService {

  @Autowired
  private SopraUserRepository sopraUserRepository;

  public SopraUser getCurrentSopraUser() {
    return sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getUsername());
  }


}
