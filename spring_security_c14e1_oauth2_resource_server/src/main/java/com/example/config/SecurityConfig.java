package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${jwksUri}")
    private String jwksUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwkSetUri(jwksUri)//uri to get the public key of signed jwt
                        .jwtAuthenticationConverter(
                                new CustomJwtAuthenticationTokenConverter())
                )
        );
        return http.build();
    }
}
