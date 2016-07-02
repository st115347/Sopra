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
    UserService userService;

    private int groupIDSave;

    @RequestMapping(value="/MCquestion", method= RequestMethod.GET)
    public String mcqestionForm (@RequestParam(value="id", required=true) Integer groupId, Model model) {

        MCquestion mcQuestion = new MCquestion();
        mcQuestion.setGroupId(groupId);
        model.addAttribute("MCquestion", mcQuestion);

        return "MCquestion";
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    //Zurodnung der MC-Frage zu konkretem Quiz fehlt noch (da Quiz noch komplett felht)
    //Autor (aktueller User) wird derzeit nicht erkannt
    @RequestMapping(value="/MCquestion", method= RequestMethod.POST)
    public String MCquestionSubmit(@Valid  @ModelAttribute MCquestion mcquestion, BindingResult result, Model model){
        if(result.hasErrors()){
            return "MCquestion";
        }

        mcquestion.setAuthor(userService.getCurrentSopraUser().getEmail());
        mcquestionRepository.save(mcquestion);
        return "/greeting";
    }


    @RequestMapping(value="/myMCquestions", method= RequestMethod.GET)
    public String mcqestionsOverview (@RequestParam(value="id", required=true) Integer groupId, Model model) {

        List<MCquestion> allMcQuestions = new ArrayList<MCquestion>(0);
        for (MCquestion mc : mcquestionRepository.findAll()){
            if((mc.getGroupId() == groupId)){
                allMcQuestions.add(mc);
            }
        }
        model.addAttribute("MCquestions", allMcQuestions);


        return "myMCquestions";
    }


    @RequestMapping(value="/changeMCquestion", method= RequestMethod.GET)
    public String changeMCquestion (@RequestParam(value="id", required=true) Integer questionId, Model model) {

        model.addAttribute("MCquestion", mcquestionRepository.findOne(questionId));


        return "MCquestion";
    }

    //Validator-Klasse oder Annotations müssen noch implementiert werden
    @RequestMapping(value="/changeMCquestion", method= RequestMethod.POST)
    public String changeMCquestionSubmit(@Valid  @ModelAttribute MCquestion mcquestion, BindingResult result, Model model){
        if(result.hasErrors()){
            return "changeMCquestion";
        }
        mcquestionRepository.save(mcquestion);
        return "/greeting";
    }



}