package com.example.config;

import com.example.config.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static javax.management.Query.and;

@Configuration
public class SecurityConfig {

    @Value("${the.secret}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .build();
    }
}


/** This is Multiple Authentication that is means
    the user can either authenticate (in this case) with http basic OR apikey.
   ** observation - on using both 1. if it is authenticated on the ApiKey -> error
                   2. if it fails on the first one you cna add programmatically
                to continue to next one.
 *

 * Custom filter uses it own Authentication Manager and all the defaults filter also use one common
  authentication manager.
    ** look up the image folder for more

 * .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class) this means
   first use ApiKeyFilter (custom one) if it doesn't use this one (no fail actually)
   then use the Default one( Http Basic).
 */


