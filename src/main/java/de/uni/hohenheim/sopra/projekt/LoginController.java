package de.uni.hohenheim.sopra.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 * Created by hilaltaylan on 07.06.16.
 */
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/Login3")
    public String Login3() {
        return "Login3";
    }

}
