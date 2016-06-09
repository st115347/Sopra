package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Sören on 05.06.2016.
 */
@Controller
public class LearningGroupController {


    @Autowired
    LearningGroupRepository learningGroupRepository;

    @Autowired
    UserRepository userRepository;



    @RequestMapping(value="/add_lgp", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("lerngruppe", new LearningGroup());
        return "add_lgp";
    }

        //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/add_lgp", method=RequestMethod.POST)
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
        return "LearningGroup";
    }


    @RequestMapping(value="/change_lgp", method=RequestMethod.GET)
    public String changeLgpForm(@RequestParam(value="id", required=true) Integer groupId, Model model){
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(groupId));
        return "change_lgp";
    }


    @RequestMapping(value="/change_lgp", method=RequestMethod.POST)
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
}