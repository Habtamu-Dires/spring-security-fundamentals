package com.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private String clientId;
    private String secret;
    @Column(name = "redirect_uri")
    private String redirectUri;
    private String scope;
    @Column(name = "auth_method")
    private String authMethod;
    @Column(name = "grant_type")
    private String grantType;

    //not clean code
    public static Client from(RegisteredClient registeredClient){
        Client client = new Client();
        client.setClientId(registeredClient.getClientId());
        client.setSecret(registeredClient.getClientSecret());
        client.setRedirectUri(
                registeredClient.getRedirectUris().stream().findAny().get()
        );
        client.setScope(
                registeredClient.getScopes().stream().findAny().get()
        );
        client.setAuthMethod(
                registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue()
        );
        client.setGrantType(
                registeredClient.getAuthorizationGrantTypes().stream().findAny().get().getValue()
        );
        return client;
    }

  public static RegisteredClient from(Client client){
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
                .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) //opaque token, default in non-opaque or jwt
                        .accessTokenTimeToLive(Duration.ofHours(24)).build())
                .build();
  }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", secret='" + secret + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", scope='" + scope + '\'' +
                ", authMethod='" + authMethod + '\'' +
                ", grantType='" + grantType + '\'' +
                '}';
    }
}
