package com.example.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailerConfig {


    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailer = new JavaMailSenderImpl();
        mailer.setHost("localhost");
        mailer.setPort(3025);
        return  mailer;
    }

    @Bean
    public SimpleMailMessage passwordReissueMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test-from@stnet.co.jp");
        message.setSubject("test-subject");
        return message;
    }

}
