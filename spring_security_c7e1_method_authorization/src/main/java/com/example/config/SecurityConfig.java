package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
//prePostEnabled enables -> @PreAuthorize @PostAuthorize @PreFilter @PostFilter
public class SecurityConfig {


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new  InMemoryUserDetailsManager();
        UserDetails user1 = User.withUsername("John")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        UserDetails user2 = User.withUsername("Bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        uds.createUser(user1);
        uds.createUser(user2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


/**
  AOP, aspect oriented programming -> based features like method authorization, Transactional
 needs to be enabled an configuration class.
 */
