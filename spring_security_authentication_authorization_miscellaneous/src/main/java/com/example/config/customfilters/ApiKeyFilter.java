package com.example.config.customfilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String headerKey = request.getHeader("x-api-key");

        if(headerKey == null){
            filterChain.doFilter(request,response);
        } else {

            //1. create authentication object
            Authentication auth = new ApiKeyAuthentication(headerKey, false);
            //2. give authentication object to authentication manager
            ApiKeyAuthenticationManager manager = new ApiKeyAuthenticationManager(key);
            Authentication authentication = manager.authenticate(auth);

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                throw new BadCredentialsException("not authenticated");
            }
        }
    }
}
