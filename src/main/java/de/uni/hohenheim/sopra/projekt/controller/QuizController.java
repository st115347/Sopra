package de.uni.hohenheim.sopra.projekt.controller;

import de.uni.hohenheim.sopra.projekt.UserService;
import de.uni.hohenheim.sopra.projekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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

    @Autowired
    TupelRepository tupelRepository;

    @Autowired
    HighscoreRepository highscoreRepository;

    @Autowired
    HtupelRepository htupelRepository;


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


      return "redirect:myMCquestions?id="+mcquestion.getGroupId();


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


            return "redirect:myMCquestions?id="+mcquestion.getGroupId();

    }

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
        model.addAttribute("user", userService.getCurrentSopraUser());

        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(id).getSopraUsers().get(0))){
            return "show_quiz_owner";
        }
        return "show_quiz";


    }
    @RequestMapping(value="deleteQuiz")
    public String deleteQuiz(@RequestParam(value="id") Integer qid, Model model){
        Integer id = quizRepository.findOne(qid).getGroupID();
        quizRepository.delete(qid);
        return "redirect:/showQuiz?id="+id;
    }

    @RequestMapping(value="doQuiz", method=RequestMethod.GET)
    public String doQuiz(@RequestParam(value="id") Integer qid,Model model){
        List<MCquestion> questions = quizRepository.findOne(qid).getQuestions();
        Integer grpid = quizRepository.findOne(qid).getGroupID();
        List<Tupel> active = new ArrayList<Tupel>();
        for(MCquestion q:questions){
            Tupel t = new Tupel();
            t.setA1(0);
            t.setA2(0);
            t.setA3(0);
            t.setA4(0);
            t.setQ(q);
            t.setQid(qid);
            tupelRepository.save(t);
            active.add(t);

        }
        userService.getCurrentSopraUser().setActive(active);
        sopraUserRepository.save(userService.getCurrentSopraUser());
        model.addAttribute("tupel", active.get(0));
        model.addAttribute("size", 1);
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(grpid));
        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(grpid).getSopraUsers().get(0))){
            return "solve_question_owner";
        }
        return "solve_question";
    }

    @RequestMapping(value="nextQuestion", method=RequestMethod.POST)
    public String showNextQuestion(@ModelAttribute Tupel tupel, Model model){
        List<Tupel> alltupel = userService.getCurrentSopraUser().getActive();
        int index=0;
        int next=0;
        for (Tupel t : alltupel){
            index = alltupel.indexOf(t);
            next = index +1;
            if (t.getQ().equals(tupel.getQ())){
                if(alltupel.indexOf(t) == (alltupel.size()-1)){

                    tupelRepository.save(tupel);
                    alltupel.set(index,tupel);
                    userService.getCurrentSopraUser().setActive(alltupel);
                    sopraUserRepository.save(userService.getCurrentSopraUser());

                    Integer total = evaluate(alltupel);
                    Quiz quiz = quizRepository.findOne(alltupel.get(0).getQid());
                    Htupel ht = new Htupel();
                    ht.setScore(total);
                    ht.setUsername(userService.getCurrentSopraUser().getVorname() + " " + userService.getCurrentSopraUser().getNachname());
                    htupelRepository.save(ht);
                    Highscore highscore = quiz.getHighscore();
                    if(highscore == null){
                    highscore = new Highscore();
                    highscore.setHtupels(new ArrayList<Htupel>());
                    }
                    highscore.setHtupels(insertUser(highscore.getHtupels(),ht));
                    highscoreRepository.save(highscore);
                    quiz.setHighscore(highscore);
                    quizRepository.save(quiz);
                    Integer place =0;
                    for (Htupel h : highscore.getHtupels()){
                        if (h.getUsername().equals(userService.getCurrentSopraUser().getVorname() + " " + userService.getCurrentSopraUser().getNachname())){
                            place = highscore.getHtupels().indexOf(h) +1;
                            break;
                        }
                    }

                    model.addAttribute("max", getMaxPkt(alltupel));
                    model.addAttribute("punkte", total);
                    model.addAttribute("place", place);
                    model.addAttribute("alltupel", alltupel);
                    model.addAttribute("lerngruppe", learningGroupRepository.findOne(t.getQ().getGroupId()));
                    if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(tupel.getQ().getGroupId()).getSopraUsers().get(0))){
                        return "end_quiz_owner";
                    }
                    return "end_quiz";
                }

                break;
            }
        }
        tupelRepository.save(tupel);
        alltupel.set(index,tupel);
        userService.getCurrentSopraUser().setActive(alltupel);
        sopraUserRepository.save(userService.getCurrentSopraUser());
        tupel = alltupel.get(next);
        model.addAttribute("tupel", tupel);
        model.addAttribute("size", next +1);
        model.addAttribute("lerngruppe", learningGroupRepository.findOne(tupel.getQ().getGroupId()));
        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(tupel.getQ().getGroupId()).getSopraUsers().get(0))){
            return "solve_question_owner";
        }
        return "solve_question";
    }

    @RequestMapping(value="showHighscore")
    public String showHighscore(@RequestParam(value="id") Integer qid, Model model){
        model.addAttribute("lerngruppe",learningGroupRepository.findOne( quizRepository.findOne(qid).getGroupID()));
        model.addAttribute("htupels", quizRepository.findOne(qid).getHighscore().getHtupels());
        if(userService.getCurrentSopraUser().equals(learningGroupRepository.findOne(quizRepository.findOne(qid).getGroupID()).getSopraUsers().get(0))){
            return "show_highscore_owner";
        }
        return "show_highscore";
    }

    public Integer evaluate(List<Tupel> list){
        int total = 0;
        for (Tupel t : list){
            if ((t.getQ().getAntwort1_lsg() != 0) && (t.getA1() != 0)){
                if(t.getA1() == t.getQ().getAntwort1_lsg()){
                    total++;
                }else{
                    total--;
                }
            }
            if ((t.getQ().getAntwort2_lsg() != 0) && (t.getA2() != 0)){
                if(t.getA2() == t.getQ().getAntwort2_lsg()){
                    total++;
                }else{
                    total--;
                }
            }
            if ((t.getQ().getAntwort3_lsg() != 0) && (t.getA3() != 0)){
                if(t.getA3() == t.getQ().getAntwort3_lsg()){
                    total++;
                }else{
                    total--;
                }
            }
            if ((t.getQ().getAntwort4_lsg() != 0) && (t.getA4() != 0)) {
                if (t.getA4() == t.getQ().getAntwort4_lsg()) {
                    total++;
                } else {
                    total--;
                }
            }
        }
        return total;
    }

    public List<Htupel> insertUser(List<Htupel> list, Htupel ht){
        list.add(ht);
        Collections.sort(list);
        return list;
    }

    public Integer getMaxPkt(List<Tupel> list){
        int total = 0;
        for(Tupel t : list){
            if (t.getQ().getAntwort1_lsg() != 0){
                total++;
            }
            if (t.getQ().getAntwort2_lsg() != 0){
                total++;
            }
            if (t.getQ().getAntwort3_lsg() != 0){
                total++;
            }
            if (t.getQ().getAntwort4_lsg() != 0){
                total++;
            }
        }
        return total;
    }
}