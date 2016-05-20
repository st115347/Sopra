package de.uni.hohenheim.sopra.projekt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Tabea on 20.05.2016.
 */
@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Unbekannter") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
