package com.example.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2
                .authenticationManagerResolver(
                        authenticationManagerResolver(jwtDecoder(),opaqueTokenIntrospector()) // manages multiple authentication provider
                )
        )
        .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated());

        return http.build();
    }
    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(
            JwtDecoder jwtDecoder, OpaqueTokenIntrospector opaqueTokenIntrospector
    ){
        AuthenticationManager jwtAuth = new ProviderManager(
                new JwtAuthenticationProvider(jwtDecoder));

        AuthenticationManager opaqueAuth = new ProviderManager(
                new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector));

        return (request) -> {
            if("jwt".equals(request.getHeader("type"))){
                return jwtAuth;
            } else {
                return opaqueAuth;
            }
        };
    }
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder
                .withJwkSetUri("http://localhost:8080/oauth2/jwks")
                .build();
    }

    public OpaqueTokenIntrospector opaqueTokenIntrospector(){
        return new SpringOpaqueTokenIntrospector(
                "http://localhost:7070/oauth2/introspect",
                "client",
                "secret");
    }

//    @Bean
//    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(){
//        // this considers all of our authorization servers issues jwt with openid connect
//        // given the issure location, where they will get the keys
//        JwtIssuerAuthenticationManagerResolver a =
//                JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers(
//                        "http://localhost:7070","http://localhost:8080"       // list of authorization server issure url
//                );
//
//        return a;
//    }


//AuthenticationManagerResolver is the one that allows to one or more provider implementation
// them based the logic we want, redirect the url to what we want
//    @Bean
//    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(){
//      AuthenticationManager a = new ProviderManager(new JwtAuthenticationProvider());
//
//        return r -> a;
//    }
}


/**
 localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://springone.io/authorized&code_challage=e1EUD3UbLMuTgaedSxse09Ri1T1L-G8cpkzx1AHIdHU&code_challage_method=S256
 localhost:7070/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://springone.io/authorized&code_challage=e1EUD3UbLMuTgaedSxse09Ri1T1L-G8cpkzx1AHIdHU&code_challage_method=S256
 */