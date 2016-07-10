package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.*;
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
import org.springframework.validation.ObjectError;
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
    ActivityRepository activityRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    private int groupIDSave;

    private boolean showgrpfullerror=false;


    @RequestMapping("/greeting")
    /**
     * Diese Methode gibt den Begrüßungstext aus.
     */
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "Unbekannter") String name, Model model) {
        SopraUser user = sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        model.addAttribute("user", user);
        return "greeting";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("sopraUser", new SopraUser());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSubmit(@Valid @ModelAttribute SopraUser sopraUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        Collection<GrantedAuthority> authsAdmin = new ArrayList<>();
        authsAdmin.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userDetailsManager.createUser(new User(sopraUser.getUsername(), passwordEncoder.encode(sopraUser.getPasswort()), authsAdmin));
        sopraUserRepository.save(sopraUser);
        return "login";
    }

    @RequestMapping(value = "/change_profileSettings", method = RequestMethod.GET)
    public String change_profileSettingsForm(Model model) {
        model.addAttribute("sopraUser", sopraUserRepository.findByUsername(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()));
        return "change_profileSettings";
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value = "/change_profileSettings", method = RequestMethod.POST)
    public String change_profileSettingsSubmit(@Valid @ModelAttribute SopraUser sopraUser_old, BindingResult result) {
        if (result.hasErrors()) {
            return "change_profileSettings";
        }
        SopraUser sopraUser_new = userService.getCurrentSopraUser();
        sopraUser_new = sopraUser_old;
        sopraUserRepository.save(sopraUser_new);

        //Neue Activity wird erzeugt
        Activity act = new Activity();
        act.setType(1);
        act.setUser(userService.getCurrentSopraUser().getEmail());
        act.generateActivity();
        activityRepository.save(act);


        return "greeting";
    }

    /**
     * Selecting all groups in which User is not yet member and displaying them
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/join_lgp")
    public String join_lgp(Model model) {

        List<LearningGroup> grp = new ArrayList<LearningGroup>();
        for (LearningGroup l : learningGroupRepository.findAll()) {
            if (l.getSopraUsers().contains(userService.getCurrentSopraUser())) {

            } else {
                grp.add(l);
            }
        }
        model.addAttribute("lerngruppe", grp);
        if(showgrpfullerror){
            model.addAttribute("error","Die Lerngruppe ist voll.");
            showgrpfullerror=false;
        }
        return "join_lgp";
    }


    /**
     * Checking if selected LearningGroup is password protected:
     * goto 'passwd_join_lgp' if yes.
     *
     * @param lgpid
     * @param model
     * @return
     */
    @RequestMapping(value = "/finish_join_lgp")
    public String gotoLGP(@RequestParam(value = "id", required = true) Integer lgpid, Model model) {
        LearningGroup grp = learningGroupRepository.findOne(lgpid);
        SopraUser user = userService.getCurrentSopraUser();
        if (!grp.getVisibility()) {
            groupIDSave = lgpid;
            model.addAttribute("Password", new Password());
            return "/passwd_join_lgp";
        } else if (!grp.addUser(user)) {
            showgrpfullerror=true;
            return "redirect:/join_lgp";
        }
        learningGroupRepository.save(grp);

        //Neue Activity wird erzeugt
        Activity act = new Activity();
        act.setType(0);
        act.setUser(userService.getCurrentSopraUser().getEmail());
        act.setVariable(grp.getName());
        act.generateActivity();
        activityRepository.save(act);

        return displayLGP(model);

    }


    /**
     * Checking if entered password corresponds to learninggroup password.
     * -> if yes: will return link to 'myLearningGroups'
     * -> if no: will return to 'join:lgp'
     *
     * @param password
     * @param model
     * @return next Website
     */
    @RequestMapping(value = "/passwd_join_lgp", method = RequestMethod.POST)
    public String protectedJoin(@ModelAttribute("Password") Password password, BindingResult result, Model model) {

        LearningGroup grp = learningGroupRepository.findOne(groupIDSave);
        SopraUser user = userService.getCurrentSopraUser();

        if (grp.comparePassword(password)) {
            if (grp.addUser(user)) {
                learningGroupRepository.save(grp);

                //Neue Activity wird erzeugt
                Activity act = new Activity();
                act.setType(0);
                act.setUser(userService.getCurrentSopraUser().getEmail());
                act.setVariable(grp.getName());
                act.generateActivity();
                activityRepository.save(act);


                return displayLGP(model);

            }
                showgrpfullerror=true;
                return "redirect:/join_lgp";


            }

            result.rejectValue("pw","error.password","Das eingegebene Passwort ist falsch");
            return "passwd_join_lgp";
        }

    /**
     * Hilfsmethode
     * Searching groups in which user is member
     * @param model
     * @return
     */
    public String displayLGP(Model model) {

    SopraUser user = userService.getCurrentSopraUser();
    List<LearningGroup> usergrps = new ArrayList<LearningGroup>();
    for(LearningGroup l :learningGroupRepository.findAll()) {
        if ((l.getSopraUsers().contains(user))) {
            usergrps.add(l);
        }
    }

    model.addAttribute("lerngruppe",usergrps);
    return"myLearningGroups";
}

    @RequestMapping(value = "/myActivities", method = RequestMethod.GET)
    public String myActivities(Model model) {

        SopraUser user = userService.getCurrentSopraUser();

        List<Activity> activities = new ArrayList<Activity>();
        for (Activity act : activityRepository.findAll()){

                activities.add(act);

        }

        model.addAttribute("activities", activities);

            return "myActivities";

        }

}
