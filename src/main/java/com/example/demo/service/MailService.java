package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(String title,String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("solid@Solid.com");
        simpleMailMessage.setTo("meki3988@gmail.com");
        simpleMailMessage.setText(title);
        simpleMailMessage.setSubject(text);
        javaMailSender.send(simpleMailMessage);
        return "success";

    }

}
