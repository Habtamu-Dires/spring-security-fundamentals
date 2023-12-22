package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetailsService(){
        var uds = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("Bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        UserDetails user2 = User.withUsername("John")
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // request matcher + authentication rule
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.GET).hasAuthority("read") // you can do this but no recommended
                        .requestMatchers(HttpMethod.GET,"/demo/**").hasAuthority("read")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/demo/[A-Z0-9]+"))//regx expression
                                .hasAuthority("write")
                        .anyRequest().permitAll()
                )
                .build();
    }
}
/**
 * Ant expression:
   // /demo/* /something  --- /demo/<anything>/something

 * .requestMatchers(HttpMethod.GET,"/demo/**").hasAuthority("read") // GET request /demo/** will only be accessed by
     user who has authority "read"

 *Matcher methods - requestMatchers()
   -> Old spring security uses mvcMatchers() and antMatchers
 */