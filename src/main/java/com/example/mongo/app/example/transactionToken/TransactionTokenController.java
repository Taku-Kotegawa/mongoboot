package com.example.mongo.app.example.transactionToken;

import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@Slf4j
@Controller
@SessionAttributes(types = {TransactionTokenForm.class})
@RequestMapping("/transactiontoken")
@TransactionTokenCheck("transactiontoken")
public class TransactionTokenController {

    @Autowired
    Mapper beanMapper;

    @GetMapping()
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(TransactionTokenForm form, Model model) {
        form.setScreenId(1);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "1")
    @TransactionTokenCheck
    public String create1(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(1);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "2")
    @TransactionTokenCheck
    public String create2(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(2);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "3")
    @TransactionTokenCheck
    public String create3(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(3);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "4")
    @TransactionTokenCheck
    public String create4(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model) {
        form.setScreenId(4);
        return "example/transactiontokenform";
    }

    @PostMapping(params = "5")
    @TransactionTokenCheck
    public String complete(@Validated TransactionTokenForm form, BindingResult bindingResult, Model model, RedirectAttributes attributes, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/transactiontoken";
    }

}
