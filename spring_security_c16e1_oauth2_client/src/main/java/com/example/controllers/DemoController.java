package com.example.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemoController {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager; // inject it in the proxy

    @GetMapping("/token")
    public String token(){

        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client")
                .build();

        //send request to the authorization server
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);

        return client.getAccessToken().getTokenValue(); // get the token here.
    }
}
