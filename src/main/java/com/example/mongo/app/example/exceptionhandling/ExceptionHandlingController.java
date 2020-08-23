package com.example.mongo.app.example.exceptionhandling;


import com.example.mongo.domain.service.example.exception.TestExceptionThrower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.gfw.common.exception.BusinessException;

@Slf4j
@Controller
@RequestMapping("exceptionhandling")
public class ExceptionHandlingController {

    @Autowired
    TestExceptionThrower testExceptionThrower;

    @GetMapping()
    public String init() {
        return "example/exceptionhandling";
    }

    @GetMapping(params = "1")
    public String event01() {
        testExceptionThrower.throwBusinessException();
        return "example/exceptionhandling";
    }

    @GetMapping(params = "2")
    public String event02() {
        testExceptionThrower.throwSystemException();
        return "example/exceptionhandling";
    }

    @GetMapping(params = "3")
    public String event03() {
        testExceptionThrower.throwResourceNotFoundException();
        return "example/exceptionhandling";
    }

    @GetMapping(params = "5")
    public String event05() {
        testExceptionThrower.throwIllegalArgumentException();
        return "example/exceptionhandling";
    }

    @GetMapping(params = "6")
    public String event06() {
        testExceptionThrower.throwIllegalStateException();
        return "example/exceptionhandling";
    }

    @GetMapping(params = "7")
    public String event07() {

        throw new NullPointerException();

    }


    @GetMapping(params = "4")
    public String event04(Model model) {
        try {
            testExceptionThrower.throwBusinessException();
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        return "example/exceptionhandling";
    }


}
