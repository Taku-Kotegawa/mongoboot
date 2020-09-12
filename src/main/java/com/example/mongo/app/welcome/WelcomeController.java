package com.example.mongo.app.welcome;

import com.example.mongo.domain.cassandra.model.TestJpaEntity;
import com.example.mongo.domain.cassandra.repository.TestRepository;
import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.service.authentication.AccountSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Date;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    SpringResourceTemplateResolver springResourceTemplateResolver;


    @GetMapping("/")
    public String welcome(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        Account account = loggedInUser.getAccount();
        model.addAttribute(account);
        model.addAttribute("name", "mynameaaaabbbb");
        model.addAttribute("test", "test");

        return "welcome/index";
    }

}
