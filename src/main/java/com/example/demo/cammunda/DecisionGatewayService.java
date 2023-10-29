package com.example.demo.cammunda;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("DecisionGatewayService")
public class DecisionGatewayService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
    }
}
