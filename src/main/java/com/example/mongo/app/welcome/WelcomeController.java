package com.example.mongo.app.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("name", "mynameaaaabbbb");
        model.addAttribute("test", "test");
        return "welcome/index.html";
    }

}
