package com.example.demo.cammunda;

import com.example.demo.service.MailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnsuccessfulMailService implements JavaDelegate {
    @Autowired
    MailService mailService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        mailService.sendMail("ilan","ilan kaydedilmedi");
        System.out.println("ilan eklenemdi");
    }
}
