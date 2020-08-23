package com.example.mongo.app.example.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("validation")
public class ValidationController {

    @ModelAttribute
    public ValidationForm setUp() {
        return new ValidationForm();
    }

    @GetMapping()
    public String createForm(ValidationForm form, Model model) {
        return "example/validation";
    }

    @PostMapping()
    public String post(@Validated ValidationForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            return "example/validation";
        }

        return "example/validation";
    }

}
