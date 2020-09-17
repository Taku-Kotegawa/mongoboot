package com.example.mongo.app.welcome;

import com.example.mongo.domain.elasticsearch.model.Article;
import com.example.mongo.domain.elasticsearch.service.EsService;
import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.service.authentication.AccountSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    SpringResourceTemplateResolver springResourceTemplateResolver;

    @Autowired
    EsService esService;


    @GetMapping("/")
    public String welcome(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        Account account = loggedInUser.getAccount();
        model.addAttribute(account);
        model.addAttribute("name", "mynameaaaabbbb");
        model.addAttribute("test", "test");

        return "welcome/index";
    }

}
