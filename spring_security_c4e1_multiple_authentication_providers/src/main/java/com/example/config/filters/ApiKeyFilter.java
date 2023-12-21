package com.example.config.filters;

import com.example.config.authentications.ApiKeyAuthentication;
import com.example.config.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // get key from request header
        String requestKey = request.getHeader("x-api-key");
        //if requestKey is null delegate to the next filter
        if(requestKey == null ){
            filterChain.doFilter(request,response);
            System.out.println("are we here");
        }
        //create authentication object
        ApiKeyAuthentication auth = new ApiKeyAuthentication(requestKey);

        //crete manager
        CustomAuthenticationManager manager =
                new CustomAuthenticationManager(key);


        try{
            Authentication authentication =  manager.authenticate(auth);
            if(authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request,response);
            } else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("not authenticated");
            }

        } catch (AuthenticationException e){
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            //add this if you want to use the next filter when this one fails
            filterChain.doFilter(request,response);

        }

    }
}
