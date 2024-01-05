package com.example.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class CustomTestSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        // take your custom values from the annotation you used on the test method
        String priority = annotation.priority();

        //this should also be custom authentication implementation
        var a = new CustomAuthentication(priority);

        SecurityContext testSecurityContext = SecurityContextHolder.createEmptyContext();
        testSecurityContext.setAuthentication(a);

        return testSecurityContext;
    }
}
