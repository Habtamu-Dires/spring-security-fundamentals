package com.example.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, CustomJwtAuthenticationToken> {


    @Override
    public CustomJwtAuthenticationToken convert(Jwt source) {
        List<String> authorities =(List<String>) source.getClaims().get("authorities");
        CustomJwtAuthenticationToken  authenticationObject =
                new CustomJwtAuthenticationToken(source, authorities.stream().map(SimpleGrantedAuthority::new).toList());

        return authenticationObject;
    }
}



/*
public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {


    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        List<String> authorities =(List<String>) source.getClaims().get("authorities");
        JwtAuthenticationToken  authenticationObject =
                new JwtAuthenticationToken(source, authorities.stream().map(SimpleGrantedAuthority::new).toList());

        return authenticationObject;
    }
}
*/
