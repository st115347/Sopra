package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabea on 20.05.2016.
 */
@Controller
public class GreetingController {

    @Autowired
    LearningGroupRepository learningGroupRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/greeting")
    /**
     * Diese Methode gibt den Begrüßungstext aus.
     */
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Unbekannter") String name, Model model) {


        model.addAttribute("name", name);
        return "greeting";
    }
}
