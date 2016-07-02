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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sören Schmidt on 29.06.2016.
 */
@Controller
public class QuizController {


    @Autowired
    MCquestionRepository mcquestionRepository;

    @Autowired
    SopraUserRepository sopraUserRepository;


    @Autowired
    LearningGroupRepository learningGroupRepository;


    @Autowired
    UserService userService;

    private int groupIDSave;

    @RequestMapping(value="/MCquestion", method= RequestMethod.GET)
    public String mcqestionForm (@RequestParam(value="id", required=true) Integer groupId, Model model) {


        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(groupId);


        MCquestion mcQuestion = new MCquestion();
        mcQuestion.setGroupId(groupId);
        model.addAttribute("MCquestion", mcQuestion);
        model.addAttribute("lerngruppe", lgp);

        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "MCquestion_owner"; }
        else{
            return "MCquestion";
        }
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    //Zurodnung der MC-Frage zu konkretem Quiz fehlt noch (da Quiz noch komplett felht)
    @RequestMapping(value="/MCquestion", method= RequestMethod.POST)
    public String MCquestionSubmit(@Valid  @ModelAttribute MCquestion mcquestion, BindingResult result, Model model){
        if(result.hasErrors()){
            return "MCquestion";
        }

        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(mcquestion.getGroupId());

        mcquestion.setAuthor(userService.getCurrentSopraUser().getVorname()+" "+userService.getCurrentSopraUser().getNachname());
        mcquestionRepository.save(mcquestion);
        model.addAttribute("lerngruppe", lgp);


        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "redirect:myMCquestions_owner?id="+mcquestion.getGroupId(); }
        else{
            return "redirect:myMCquestions?id="+mcquestion.getGroupId();
        }

    }


    @RequestMapping(value="/myMCquestions", method= RequestMethod.GET)
    public String mcqestionsOverview (@RequestParam(value="id", required=true) Integer groupId, Model model) {

        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(groupId);


        List<MCquestion> allMcQuestions = new ArrayList<MCquestion>(0);
        for (MCquestion mc : mcquestionRepository.findAll()){
            if((mc.getGroupId() == groupId)){
                allMcQuestions.add(mc);
            }
        }
        model.addAttribute("MCquestions", allMcQuestions);
        model.addAttribute("lerngruppe", lgp);

        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "myMCquestions_owner"; }
        else{
            return "myMCquestions";
        }
    }


    @RequestMapping(value="/myMCquestions_owner", method= RequestMethod.GET)
    public String mcqestionsOverview_owner (@RequestParam(value="id", required=true) Integer groupId, Model model) {

        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(groupId);


        List<MCquestion> allMcQuestions = new ArrayList<MCquestion>(0);
        for (MCquestion mc : mcquestionRepository.findAll()){
            if((mc.getGroupId() == groupId)){
                allMcQuestions.add(mc);
            }
        }
        model.addAttribute("MCquestions", allMcQuestions);
        model.addAttribute("lerngruppe", lgp);

        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "myMCquestions_owner"; }
        else{
            return "myMCquestions";
        }
    }


    @RequestMapping(value="/changeMCquestion", method= RequestMethod.GET)
    public String changeMCquestion (@RequestParam(value="id", required=true) Integer questionId, Model model) {


        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(mcquestionRepository.findOne(questionId).getGroupId());


        model.addAttribute("MCquestion", mcquestionRepository.findOne(questionId));
        model.addAttribute("lerngruppe", lgp);

        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "MCquestion_owner"; }
        else{
            return "MCquestion";
        }
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/changeMCquestion", method= RequestMethod.POST)
    public String changeMCquestionSubmit(@Valid  @ModelAttribute MCquestion mcquestion, BindingResult result, Model model){
        if(result.hasErrors()){
            return "changeMCquestion";
        }
        SopraUser user = userService.getCurrentSopraUser();
        LearningGroup lgp = learningGroupRepository.findOne(mcquestion.getGroupId());

        mcquestionRepository.save(mcquestion);
        model.addAttribute("lerngruppe", lgp);


        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "redirect:myMCquestions_owner?id="+mcquestion.getGroupId(); }
        else{
            return "redirect:myMCquestions?id="+mcquestion.getGroupId();
        }
    }



}