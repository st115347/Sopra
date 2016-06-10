package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.LearningGroupRepository;
import de.uni.hohenheim.sopra.projekt.model.SopraUser;
import de.uni.hohenheim.sopra.projekt.model.SopraUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import de.uni.hohenheim.sopra.projekt.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tabea on 20.05.2016.
 */
@Controller
public class GreetingController {

    @Autowired
    LearningGroupRepository learningGroupRepository;

    @Autowired
    SopraUserRepository sopraUserRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @RequestMapping("/greeting")
    /**
     * Diese Methode gibt den Begrüßungstext aus.
     */
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Unbekannter") String name, Model model) {


        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("sopraUser", new SopraUser());
        return "register";
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public String registerSubmit(@Valid @ModelAttribute SopraUser sopraUser, BindingResult result) {
        if(result.hasErrors()){
            return "register";
        }
        Collection<GrantedAuthority> authsAdmin = new ArrayList<>();
        authsAdmin.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userDetailsManager.createUser(new User(sopraUser.getUsername(), passwordEncoder.encode(sopraUser.getPasswort()), authsAdmin));
        sopraUserRepository.save(sopraUser);
        return "login";
    }

    @RequestMapping(value="/change_profileSettings", method= RequestMethod.GET)
    public String change_profileSettingsForm(Model model){
        model.addAttribute("sopraUser", sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()));
        return "change_profileSettings";
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/change_profileSettings", method= RequestMethod.POST)
    public String change_profileSettingsSubmit(@Valid @ModelAttribute SopraUser sopraUser_old, BindingResult result){
        if(result.hasErrors()){
            return "change_profileSettings";
        }
        SopraUser sopraUser_new = sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        sopraUser_new = sopraUser_old;
        sopraUserRepository.save(sopraUser_new);
        return "greeting";
    }


}
