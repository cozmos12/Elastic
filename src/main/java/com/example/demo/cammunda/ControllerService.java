package com.example.demo.cammunda;

import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


@Slf4j
@Named(value="ControllerService")
public class ControllerService implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
            log.info("Controller service call is successful.");
            System.out.println("Controller service");
    }
}
