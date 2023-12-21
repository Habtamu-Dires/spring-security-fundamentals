package com.example.config.security.filter;

import com.example.config.security.manager.CustomAuthenticationManager;
import com.example.config.security.authentication.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1.create authentication object which is not yet authenticated
        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication customAuthentication = new CustomAuthentication(false, key);

        // 2.delegate the authentication object to the manager
        // 3.and get back the authentication object from the manager
        Authentication authenticationObject =
                customAuthenticationManager.authenticate(customAuthentication);

        // 4.if the object is authenticated then send request to the next filter in the chain
        if(authenticationObject.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationObject); // store the authentication , so that authorization checkout who is
            filterChain.doFilter(request, response); // propagate to the next filter in the filter chain
        }
    }
}


/**
 * We could have implement jakarta.servlet.Filter but to make sure it will only be once called.
 */
