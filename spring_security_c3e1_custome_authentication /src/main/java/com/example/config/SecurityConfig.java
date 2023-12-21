package com.example.config;


import com.example.config.security.filter.CustomAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()// this related authorization.
                .and()
                .build();

    }
}


/**
 * Creating the filter , where to add
  // addFilterAt() the filter chain add new filter at the position  UsernamePasswordAuthenticationFilter
   would have stayed,
 //replace UsernamePasswordAuthenticationFilter(default )used by http basic by customAuthenticationFilter
 .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
 */
