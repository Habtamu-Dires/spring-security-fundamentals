package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((authorize)->authorize
//                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
//                        .anyRequest().denyAll()
//                        .anyRequest().hasAuthority("read")
//                        .anyRequest().hasAnyAuthority("read", "write")
//                        .anyRequest().hasRole("ADMIN")
//                        .anyRequest().hasAnyRole("ADMIN", "MANAGER")
//                        .anyRequest().access() // didn't got it?
                        .requestMatchers("/demo").hasAuthority("read")
                        .anyRequest().authenticated()
                )
                .build();
        // matcher method + authorization rule
        // things to learn
        // 1. which matcher methods should you use and how
        // 2. how to apply different authorization rules
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var uds = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("Bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
//                .roles("ADMIN")  // it will prefix with ROLE_
                .build();

        UserDetails user2 = User.withUsername("John")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
//                .roles("MANAGER") // is equivalent with authority named ROLE_MANAGER
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
 * Authorization rules
 # authenticated() -> expects the authentication object in SecurityContextHolder,
        in other word, the user should be authenticated.
 # permitAll() -> does n't need authentication , it works without authentication.
   Note: permitAll also works with authentication, however if it used with authentication
   it should be used with the correct credentials.
 - why does it work with no authentication but fails with authentication with wrong credentials.
   That is because: if we don't use the authentication the request will jump authentication filter
   and directly goes authorization then to the controller. and since it permitAll, endpoint will be
   accessed. however, if we use authentication with wrong credentials then the request will enter
   to the authentication filter and got rejected (401) by this filter before reaching to the
   authorization filter.
   -Remember authorization is always after authentication.

 # denyAll() - deny all
 # hasAuthority("red") -> specific authority only this case read
 Note: 401 - unauthorized , :) but this happens when the authentication fails
       403 - forbidden - authorization fails, but the authentication is passed

 #hansAnyAuthority(String... authority) -> uses vararg to accept any number of authority and to allow them.

 #Note: role and authority in UserDetails are technically the same
    -> both are implementation's of GrantedAuthority contract.

 #access() -> The request uses this custom AuthorizationManager to determine access.

 */

