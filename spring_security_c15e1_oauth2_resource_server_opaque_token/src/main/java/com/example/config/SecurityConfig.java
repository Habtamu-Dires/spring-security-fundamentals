package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(opaque -> opaque
                        .introspectionUri("http://localhost:8080/oauth2/introspect")
                        .introspectionClientCredentials("client", "secret")
                )
            )
            .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated()
            );

        return http.build();
    }
}
