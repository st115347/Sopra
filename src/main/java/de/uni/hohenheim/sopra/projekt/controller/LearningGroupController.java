package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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



    @RequestMapping(value="/add_lgp", method= RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("lerngruppe", new LearningGroup());
        return "add_lgp";
    }

        //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/add_lgp", method= RequestMethod.POST)
    public String greetingSubmit(@Valid @ModelAttribute LearningGroup lerngruppe, BindingResult result) {
            if(result.hasErrors()){
                return "add_lgp";
            }
        learningGroupRepository.save(lerngruppe);
        return "greeting";
    }


    @RequestMapping("/myLearningGroups")
    public String myLearningGroups(Model model){
       model.addAttribute("lerngruppe", learningGroupRepository.findAll());
        return "myLearningGroups";
    }


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
        lerngruppe_new.setVisibility(lerngruppe_old.getVisibility());
        lerngruppe_new.setName(lerngruppe_old.getName());
        learningGroupRepository.save(lerngruppe_new);
        return "/greeting";
    }


    @RequestMapping(value="/add_beitrag", method= RequestMethod.GET)
    public String add_beitragForm(@RequestParam(value="id", required=true) Integer groupId, Model model){
        Beitrag beitrag = new Beitrag();
        beitrag.setGroupId(groupId);
        model.addAttribute("beitrag", beitrag);
        return "add_beitrag";
    }

    @RequestMapping(value="/add_beitrag", method= RequestMethod.POST)
    public String add_beitragSubmit(@Valid @ModelAttribute Beitrag beitrag, BindingResult result){
        if(result.hasErrors()){
            return "add_beitrag";
        }
        beitragRepository.save(beitrag);
        return "greeting";
    }

    @RequestMapping(value="/del_lgp", method= RequestMethod.GET)
    public String del_lgp(@RequestParam(value="id", required=true) Integer groupId, Model model){
        learningGroupRepository.delete(groupId);
        return "myLearningGroups";
    }

    @RequestMapping(value="/add_lgpUser", method= RequestMethod.GET)
    public String add_lgpUserForm(@RequestParam(value="id", required=true) Integer groupId, Model model){
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(groupId));
        return "add_lgpUser";
    }

    //muss noch implementiert werden
    @RequestMapping(value="/add_lgpUser", method= RequestMethod.POST)
    public String add_lgpUserSubmit(){
       return "greeting";
    }
}