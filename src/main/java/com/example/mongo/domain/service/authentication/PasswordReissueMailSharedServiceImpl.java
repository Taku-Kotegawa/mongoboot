package com.example.mongo.domain.service.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Service
public class PasswordReissueMailSharedServiceImpl implements PasswordReissueMailSharedService {

    @Inject
    JavaMailSender mailSender;

    @Inject
    @Named("passwordReissueMessage")
    SimpleMailMessage templateMessage;

    @Override
    public void send(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
    }
}
