package com.example.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomTestSecurityContextFactory.class)
public @interface WithCustomMockUser {

    String priority() default "HIGH";
}
