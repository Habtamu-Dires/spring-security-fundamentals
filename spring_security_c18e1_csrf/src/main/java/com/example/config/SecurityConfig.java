package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
        );
//        http.csrf(csrf -> csrf.disable());
        //ignore csrf for specific path
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/smth/**"));
        //require csrf for specific path
//        http.csrf(csrf -> csrf.requireCsrfProtectionMatcher());

        return http.build();
    }
}
