package com.example.config.authentications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
public class ApiKeyAuthentication implements Authentication {


    private final String key;

    private boolean authenticated;


    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
            this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        System.out.println("no way -------------------------------------------------- ");
        return null;
    }

    public String getKey() {
        return key;
    }
}
