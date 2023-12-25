package com.example.config.customfilters;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class ApiKeyAuthenticationManager implements AuthenticationManager {

    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider = new ApiKeyAuthenticationProvider(key);
        if(provider.supports(authentication.getClass())){
                return provider.authenticate(authentication);
        }
        throw new BadCredentialsException("No supported");
    }
}
