package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören on 05.06.2016.
 */
@Controller
public class LearningGroupController {


    @Autowired
    LearningGroupRepository learningGroupRepository;

    @Autowired
    SopraUserRepository sopraUserRepository;

    @Autowired
    BeitragRepository beitragRepository;

    @Autowired
    UserService userService;

    @Autowired
    Antwort_BeitragRepository antwort_beitragRepository;

    private int groupIDSave;

    @RequestMapping(value="/add_lgp", method= RequestMethod.GET)
    public String addLearningGroupForm(Model model) {
        model.addAttribute("lerngruppe", new LearningGroup());
        return "add_lgp";
    }

    /**
     *  Checking if visibility should be true or false,
     *  saving learninggroup
     * @param lerngruppe
     * @param result
     * @return
     */

    @RequestMapping(value="/add_lgp", method= RequestMethod.POST)
    public String addLearningGroupSubmit(@Valid @ModelAttribute("lerngruppe") LearningGroup lerngruppe, BindingResult result, Model model) {
            if(result.hasErrors()){
                return "add_lgp";
            }
        Password p = new Password();
        p.setPw("");
        if(lerngruppe.comparePassword(p)){
            lerngruppe.setVisibility(true);
        }
        else{
            lerngruppe.setVisibility(false);
        }

        lerngruppe.addUser(userService.getCurrentSopraUser());
        learningGroupRepository.save(lerngruppe);
        return "greeting";
    }

    /**
     * Selecting only learninggroups in which user is member.
     * @param model
     * @return
     */
    @RequestMapping("/myLearningGroups")
    public String myLearningGroups(Model model){
        SopraUser user = userService.getCurrentSopraUser();
        List<LearningGroup> usergrps = new ArrayList<LearningGroup>();
        for (LearningGroup l : learningGroupRepository.findAll()){
            if((l.getSopraUsers().contains(user))){
                usergrps.add(l);
            }
        }
        model.addAttribute("lerngruppe", usergrps);
        return "myLearningGroups";

    }

    /**
     * Selecting learninggroup and all postings.
     * In LearningGroup.html Thymeleaf iterates over all postings and selects only the matching ones to the groupId
     * @param groupId
     * @param model
     * @return
     */
    @RequestMapping("/get_lgp")
    public String getLearninggroup(@RequestParam(value="id", required=true) Integer groupId, Model model){
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(groupId));
        model.addAttribute("beitrag", beitragRepository.findAll());
        return "LearningGroup";
    }


    @RequestMapping(value="/change_lgp", method= RequestMethod.GET)
    public String changeLgpForm(@RequestParam(value="id", required=true) Integer groupId, Model model){
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(groupId));
        return "change_lgp";
    }


    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/change_lgp", method= RequestMethod.POST)
    public String changeLgpSubmit(@Valid  @ModelAttribute LearningGroup lerngruppe_old, BindingResult result){
        Integer groupId = lerngruppe_old.getId();
        if(result.hasErrors()){
            return "change_lgp?id="+groupId;
        }
        LearningGroup lerngruppe_new = learningGroupRepository.findOne(groupId);

        Password p = new Password();
        p.setPw("");
        if(lerngruppe_old.comparePassword(p)){
            lerngruppe_new.setVisibility(true);
        }
        else{
            lerngruppe_new.setVisibility(false);
        }
        lerngruppe_new.setPassword(lerngruppe_old.getPassword());
        lerngruppe_new.setName(lerngruppe_old.getName());
        learningGroupRepository.save(lerngruppe_new);
        return "/greeting";
    }


    /**
     * Creating new beitrag and sending it to form to be filled in.
     * @param groupId
     * @param model
     * @return
     */
    @RequestMapping(value="/add_beitrag", method= RequestMethod.GET)
    public String add_beitragForm(@RequestParam(value="id", required=true) Integer groupId, Model model){
        Beitrag beitrag = new Beitrag();
        beitrag.setGroupId(groupId);
        model.addAttribute("beitrag", beitrag);
        return "add_beitrag";
    }


    /**
     * Setting author of beitrag as current user.
     * saving beitrag
     * going to showbeitrag
     * @param beitrag
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value="/add_beitrag", method= RequestMethod.POST)
    public String add_beitragSubmit(@Valid @ModelAttribute Beitrag beitrag, BindingResult result,Model model){
        if(result.hasErrors()){
            return "add_beitrag";
        }
        SopraUser user = userService.getCurrentSopraUser();
        beitrag.setAuthor(user.getVorname() + " "+ user.getNachname());

        beitragRepository.save(beitrag);
        model.addAttribute("beitrag",beitrag);
        return "show_beitrag";
    }

    /**
     * Only deleting if current user is also owner of group
     * then going to myLearninggroups
     * @param groupId
     * @param model
     * @return
     */
    @RequestMapping(value="/del_lgp", method= RequestMethod.GET)
    public String del_lgp(@RequestParam(value="id", required=true) Integer groupId, Model model){
        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(groupId);
        if (!(lgp.getSopraUsers().get(0).equals(user))){
            List<LearningGroup> usergrps = new ArrayList<LearningGroup>();
            for (LearningGroup l : learningGroupRepository.findAll()){
                if((l.getSopraUsers().contains(user))){
                    usergrps.add(l);
                }
            }
            model.addAttribute("lerngruppe", usergrps);
            return "myLearningGroups";
        }
        learningGroupRepository.delete(lgp);
        List<LearningGroup> usergrps = new ArrayList<LearningGroup>();
        for (LearningGroup l : learningGroupRepository.findAll()){
            if((l.getSopraUsers().contains(user))){
                usergrps.add(l);
            }
        }
        model.addAttribute("lerngruppe", usergrps);
        return "myLearningGroups";

    }



    /**
     * Selecting Beitrag and displaying it.
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/show_beitrag")
    public String showBeitrag(@RequestParam(value = "id") Integer id, Model model){
        Beitrag b = beitragRepository.findOne(id);
        model.addAttribute("beitrag", b);
        return "show_beitrag";
    }


    /**
     * Creating new Answer to Beitrag,
     * short-term saving GroupID into instance variable.
     * sending Answer to form to be filled in.
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/answer_beitrag", method=RequestMethod.GET)
    public String answerBeitragStart(@RequestParam(value="id") Integer id, Model model){
        Antwort_Beitrag answer = new Antwort_Beitrag();
        groupIDSave = id;
        model.addAttribute("answer",answer);
        return "answer_beitrag";
    }

    /**
     * Setting author of answer as current user, setting answer as answer to beitrag,
     * saving beitrag and answer.
     * going to show_beitrag
     * @param answer
     * @param model
     * @return
     */
    @RequestMapping(value="/answer_beitrag", method=RequestMethod.POST)
    public String answerBeitragFinish(@Valid @ModelAttribute("answer") Antwort_Beitrag answer, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "answer_beitrag";
        }

        if(answer.getTo().equals("")){
            answer.setTo("All");
        }
        SopraUser user = userService.getCurrentSopraUser();
        answer.setAuthor(user.getVorname()+" "+user.getNachname());
        Beitrag b = beitragRepository.findOne(groupIDSave);
        antwort_beitragRepository.save(answer);
        b.setAnswers(answer);
        beitragRepository.save(b);
        model.addAttribute("beitrag",b);
        return "show_beitrag";
    }

    /**
     * Displaying memberlist in learninggroup
     * @param groupId
     * @param model
     * @return
     */
   @RequestMapping(value="/show_user_lgp")
   public String showUser(@RequestParam(value="id", required=true) Integer groupId, Model model){
        LearningGroup lgp = learningGroupRepository.findOne(groupId);
            if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {

                List<SopraUser> memberList = lgp.getSopraUsers();
                memberList.remove(0);
                model.addAttribute("users",memberList);
                model.addAttribute("lerngruppe",lgp);
                groupIDSave = groupId;
                return "show_user_ownerlgp";
            }
       model.addAttribute("users", lgp.getSopraUsers());
       model.addAttribute("lerngruppe",lgp);
        return "show_user_lgp";
   }

    /**
     * Deleting user from learninggroup only if current user is also owner of group
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value="/del_user")
    public String del_user(@RequestParam (value ="id")String userId, Model model) {
        LearningGroup lgp = learningGroupRepository.findOne(groupIDSave);
        lgp.removeUser(sopraUserRepository.findByUsername(userId));
        learningGroupRepository.save(lgp);
        model.addAttribute("lerngruppe", lgp);
        List<SopraUser> memberList = lgp.getSopraUsers();
        memberList.remove(0);
        model.addAttribute("users",memberList);
        model.addAttribute("lerngruppe",lgp);
        groupIDSave = lgp.getId();
        return "show_user_ownerlgp";
    }


}