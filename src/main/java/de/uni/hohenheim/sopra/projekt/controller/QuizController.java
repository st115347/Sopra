package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    QuizRepository quizRepository;

    @Autowired
    UserService userService;

    @Autowired
    BeitragRepository   beitragRepository;

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
        mcquestion.setAuthorID(user.getUsername());
        mcquestionRepository.save(mcquestion);
        model.addAttribute("lerngruppe", lgp);


/*        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "redirect:myMCquestions_owner?id="+mcquestion.getGroupId(); }
        else{
*/            return "redirect:myMCquestions?id="+mcquestion.getGroupId();


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
        Quiz quiz = new Quiz();
        quiz.setAuthorID(user.getUsername());
        quiz.setAuthor(user.getVorname()+" "+user.getNachname());
        quiz.setFinished(new Boolean(false));
        quiz.setGroupID(lgp.getId());
        model.addAttribute("MCquestions", allMcQuestions);
        model.addAttribute("lerngruppe", lgp);
        model.addAttribute("user", user);
        model.addAttribute("quiz", quiz);
        model.addAttribute("size", 0);
        quizRepository.save(quiz);

        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "myMCquestions_owner"; }
        else{
            return "myMCquestions";
        }
    }

/*
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
        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "myMCquestions_owner"; }
        else{
            return "myMCquestions";
        }
    }
*/
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


/*        if(lgp.getSopraUsers().get(0).equals(userService.getCurrentSopraUser())) {
            return "redirect:myMCquestions_owner?id="+mcquestion.getGroupId(); }
        else{
*/
            return "redirect:myMCquestions?id="+mcquestion.getGroupId();

    }
/*
    @RequestMapping(value="/myMCquestions", method =RequestMethod.POST)
    public String createQuiz(@Valid @ModelAttribute("addQuestion") ListHelper info, BindingResult result, Model model){

        List<MCquestion> allMcQuestions = new ArrayList<MCquestion>(0);
        for (MCquestion mc : mcquestionRepository.findAll()){
            if((mc.getGroupId() == groupIDSave)){
                allMcQuestions.add(mc);
            }
        }
        List<Boolean> addQuestions = info.getList();
        List<MCquestion> quizQuestions = new ArrayList<MCquestion>();
        for (int i = 0; i < addQuestions.size(); i++){
            if (addQuestions.get(i)){
                quizQuestions.add(allMcQuestions.get(i));
            }
        }
        SopraUser user = userService.getCurrentSopraUser();
        Quiz quiz = new Quiz();
        quiz.setAuthor(user.getVorname() + user.getNachname());
        quiz.setAuthorID(user.getUsername());
        quiz.setQuestions(quizQuestions);
        quizRepository.save(quiz);

        LearningGroup lgp = learningGroupRepository.findOne(groupIDSave);
        model.addAttribute("lerngruppe", lgp);
        model.addAttribute("beitrag", beitragRepository.findAll());
        if (lgp.getSopraUsers().get(0).equals(user)) {
            return "LearningGroup_owner";
        }
        return "LearningGroup";
    }

    @RequestMapping(value="/myMCquestions_owner", method =RequestMethod.POST)
    public String createQuizOwner(@Valid @ModelAttribute("addQuestion") ListHelper info, BindingResult result, Model model){

        List<MCquestion> allMcQuestions = new ArrayList<MCquestion>(0);
        for (MCquestion mc : mcquestionRepository.findAll()){
            if((mc.getGroupId() == groupIDSave)){
                allMcQuestions.add(mc);
            }
        }
        List<Boolean> addQuestions = info.getList();
        List<MCquestion> quizQuestions = new ArrayList<MCquestion>();
        for (int i = 0; i < addQuestions.size(); i++){
            if (addQuestions.get(i)){
                quizQuestions.add(allMcQuestions.get(i));
            }
        }


        SopraUser user = userService.getCurrentSopraUser();
        Quiz quiz = new Quiz();
        quiz.setAuthor(user.getVorname() + user.getNachname());
        quiz.setAuthorID(user.getUsername());
        quiz.setQuestions(quizQuestions);
        quizRepository.save(quiz);

        LearningGroup lgp = learningGroupRepository.findOne(groupIDSave);
        model.addAttribute("lerngruppe", lgp);
        model.addAttribute("beitrag", beitragRepository.findAll());
        if (lgp.getSopraUsers().get(0).equals(user)) {
            return "LearningGroup_owner";
        }
        return "LearningGroup";
    }
*/
    @RequestMapping(value="/add_question")
    public String addQuestion(@RequestParam(value="id")Integer id,@RequestParam(value="qid") Integer qid, Model model){
        Quiz quiz = quizRepository.findOne(qid);
        MCquestion question = mcquestionRepository.findOne(id);
        List<MCquestion> list = quiz.getQuestions();
        list.add(question);
        quiz.setQuestions(list);
        quizRepository.save(quiz);

        Integer lgpid = question.getGroupId();


        return"redirect:/showMCQuestions?id="+lgpid+"&qid="+quiz.getId();

    }

    @RequestMapping(value="showMCQuestions")
    public String showQuestions(@RequestParam(value="id")Integer lgpid, @RequestParam(value="qid")Integer qid, Model model){
        Quiz quiz = quizRepository.findOne(qid);

        List<MCquestion> all = mcquestionRepository.findAll();
        List<MCquestion> list = quiz.getQuestions();
        for(MCquestion q : list){
            all.remove(q);
        }

        List<MCquestion> groupquestions = new ArrayList<MCquestion>();
        for(MCquestion q : all){
            if (q.getGroupId() == lgpid){
                groupquestions.add(q);
            }
        }
        model.addAttribute("MCquestions", groupquestions);
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(lgpid));
        model.addAttribute("user", userService.getCurrentSopraUser());
        model.addAttribute("quiz", quiz);
        model.addAttribute("size", quiz.getQuestions().size());

        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(lgpid).getSopraUsers().get(0))){
            return "myMCquestions_owner";
        }
        return "myMCquestions";

    }

    @RequestMapping(value="/create_quiz", method = RequestMethod.GET)
    public String createQuiz(@RequestParam(value="id") Integer qid, Model model){
        Quiz quiz = quizRepository.findOne(qid);
        model.addAttribute("quiz",quiz);
        model.addAttribute("MCquestions", quiz.getQuestions());
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(quiz.getGroupID()));

        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(quiz.getGroupID()).getSopraUsers().get(0))){
            return "create_quiz_owner";
        }
        return "create_quiz";
    }

    @RequestMapping(value="/create_quiz", method=RequestMethod.POST)
    public String finishCreateQuiz(@ModelAttribute Quiz quiz,Model model){
        quiz.setFinished(true);
        quizRepository.save(quiz);
        return"redirect:/get_lgp?id="+quiz.getGroupID();
    }

    @RequestMapping(value="/showQuiz")
    public String showQuiz(@RequestParam(value="id")Integer id,Model model){
        List<Quiz> all = quizRepository.findAll();
        List<Quiz> allquiz = new ArrayList<Quiz>();
        for(Quiz q : all){
            if(q.getGroupID()==id && q.getFinished()){
                allquiz.add(q);
            }
        }
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(id));
        model.addAttribute("allquiz",allquiz);

        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(id).getSopraUsers().get(0))){
            return "show_quiz_owner";
        }
        return "show_quiz";


    }

}