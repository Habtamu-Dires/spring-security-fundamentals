package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(outh2 -> outh2
                .jwt(jwt -> jwt
                        .jwkSetUri("http://example.com"))
        );

        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated());
        return http.build();
    }
}
