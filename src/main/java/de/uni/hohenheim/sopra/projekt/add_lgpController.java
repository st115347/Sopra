package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Sören on 05.06.2016.
 */
@Controller
public class add_lgpController {


    @Autowired
    LearningGroupRepository learningGroupRepository;

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/add_lgp")
    public String add_lgp() {
        return "add_lgp";
    }


    @RequestMapping("/add_lgp_do")
    public String add_lgp_do(@RequestParam(value = "Name_Lerngruppe",
            required = true) String lerngruppe) {
        LearningGroup lg1 = new LearningGroup();
        lg1.setName(lerngruppe);
        //set visibility muss noch implementiert werden


          return "greeting";

    }
}