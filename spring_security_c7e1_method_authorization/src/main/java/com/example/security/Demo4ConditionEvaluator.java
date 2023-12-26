package com.example.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Demo4ConditionEvaluator {

    public boolean condition(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return true;    // your complex authorization condition
    }
}
