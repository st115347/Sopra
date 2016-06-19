package de.uni.hohenheim.sopra.projekt.controller;

import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bumiller on 06.06.2016.
 */
@Controller
public class LoginController extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }


  @RequestMapping(value = "/admin/home")
  public String adminHome() {
    return "adminhome";
  }

  @RequestMapping(value = "/home")
  public String userHome() {
    return "userhome";
  }

  /**
   * Request mapping for the disputeResolutionOverview page or administrationOverview page depending on user role.
   * Propagates to respective methods
   *
   * @return home url?
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String displayHome() {
    if (((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      return "/greeting";
    } else {
      return "/greeting";
    }


  }
/*
 * Logout method
 *
 */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }
}




