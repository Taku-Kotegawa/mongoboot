package com.example.mongo.app.example.guruguru;

import com.example.mongo.domain.model.authentication.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("guruauth")
@Controller
public class GuruAuthController {

    @ModelAttribute
    public GuruguruForm setUp() {
        return new GuruguruForm();
    }

    @GetMapping()
    public String init(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "example/guruauth";
    }

    @PostMapping(params = "1")
    public String doEvent001(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        return "example/guruauth";
    }


}
