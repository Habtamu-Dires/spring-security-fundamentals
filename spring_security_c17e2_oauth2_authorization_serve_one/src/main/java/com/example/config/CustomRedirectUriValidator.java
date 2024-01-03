package com.example.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.function.Consumer;

public class CustomRedirectUriValidator implements
            Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> {

    /* OAuth2AuthorizationCodeRequestAuthenticationContext, refers to first request from
      client, where we can able to see the redirect uri amd others
     */

    @Override
    public void accept(OAuth2AuthorizationCodeRequestAuthenticationContext context) {
        //get details from the authentication
        OAuth2AuthorizationCodeRequestAuthenticationToken authentication =
                context.getAuthentication();
        String uri = authentication.getRedirectUri();
        //get details from the registered client
        RegisteredClient registeredClient = context.getRegisteredClient();

        //check if the redirect uri exists in registered clients redirect uri list
        if(!registeredClient.getRedirectUris().contains(uri)){
            var error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
            throw new OAuth2AuthorizationCodeRequestAuthenticationException(error, null);
        }
    }
}
/**
 * local host is not supported by default as redirect uri,
    we need to configure it to support them
 */
