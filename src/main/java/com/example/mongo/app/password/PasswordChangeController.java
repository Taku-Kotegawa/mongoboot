package com.example.mongo.app.password;


import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.service.authentication.PasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("password")
public class PasswordChangeController {

    @Autowired
    PasswordChangeService passwordChangeService;

    @ModelAttribute("passwordChangeForm")
    public PasswordChangeForm setUpPasswordChangeForm() {
        return new PasswordChangeForm();
    }

    @GetMapping(params = "form")
    public String showForm(PasswordChangeForm form,
                           @AuthenticationPrincipal LoggedInUser userDetails, Model model) {

        Account account = userDetails.getAccount();
        model.addAttribute("account", account);
        return "passwordchange/changeForm";
    }

    @PostMapping
    public String change(@AuthenticationPrincipal LoggedInUser userDetails,
                         @Validated PasswordChangeForm form,
                         BindingResult bindingResult, Model model) {

        Account account = userDetails.getAccount();
        if (bindingResult.hasErrors() || !account.getUsername().equals(form.getUsername())) {
            model.addAttribute("account", account);
            return "passwordchange/changeForm";
        }

        passwordChangeService.updatePassword(form.getUsername(), form.getNewPassword());

        return "redirect:/password?complete";

    }

    @GetMapping(params = "complete")
    public String changeComplete() {
        return "passwordchange/changeComplete";
    }
}
