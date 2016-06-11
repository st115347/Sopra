package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.LearningGroup;
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
import java.util.List;

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
    @Autowired
    UserService userService;


    @RequestMapping("/greeting")
    /**
     * Diese Methode gibt den Begrüßungstext aus.
     */
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Unbekannter") String name, Model model) {
        SopraUser user = sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        model.addAttribute("user", user);
        return "greeting";
    }

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String registerForm(Model model) {
        SopraUser user = new SopraUser();
        Collection<GrantedAuthority> authsAdmin = new ArrayList<>();
        authsAdmin.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userDetailsManager.createUser(new User("a", passwordEncoder.encode("b"), authsAdmin));
        user.setUsername("a");
        user.setPasswort("b");
        user.setEmail("a");
        sopraUserRepository.save(user);

        SopraUser s = new SopraUser();
        LearningGroup l = new LearningGroup();
        l.setId(1);
        l.setName("haha");
        l.setVisibility(true);
        LearningGroup l1 = new LearningGroup();
        l1.setId(2);
        l1.setName("asdf");
        l1.setVisibility(true);
        LearningGroup l2 = new LearningGroup();
        l2.setId(3);
        l2.setName("hsgh");
        l2.setVisibility(false);
        LearningGroup l3 = new LearningGroup();
        l3.setId(4);
        l3.setName("asfafs");
        l3.setVisibility(true);
        learningGroupRepository.save(l);
        learningGroupRepository.save(l1);
        learningGroupRepository.save(l2);
        learningGroupRepository.save(l3);

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

    @RequestMapping(value="/join_lgp")
    public String displayLGP(Model model){

        List<LearningGroup> grp = new ArrayList<LearningGroup>();
        for (LearningGroup l : learningGroupRepository.findAll()){
            if (l.getSopraUsers().contains(userService.getCurrentSopraUser())) {

            }else {
                grp.add(l);
            }
        }


        model.addAttribute("lerngruppe", grp);
        return "join_lgp";
    }

    @RequestMapping(value ="/finish_join_lgp")
    public String gotoLGP(@RequestParam(value = "id", required = true) Integer lgpid, Model model){
        LearningGroup grp = learningGroupRepository.findOne(lgpid);
        SopraUser user = userService.getCurrentSopraUser();
        if(!grp.addUser(user)){
            return "/join_lgp";
        }
        learningGroupRepository.save(grp);
        List<LearningGroup> usergrps = new ArrayList<LearningGroup>();
        for (LearningGroup l : learningGroupRepository.findAll()){
            if((l.getSopraUsers().contains(user))){
                usergrps.add(l);
            }
        }
        model.addAttribute("lerngruppe", usergrps);
        return "myLearningGroups";

    }


}
