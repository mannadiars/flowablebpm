package com.flowable.fltest;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ProcessOrder implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("Send approval email! -> " + delegateExecution.getId() + ", tax " + delegateExecution.getVariable("tax"));
    }
}
