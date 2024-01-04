package com.example.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class DemoController {

    @GetMapping(value = "/demo")
//    @PreAuthorize("hasAuthority('read')")
    public Flux<Integer> demo(){
        return Flux.just(1,2,3,4,5,7)
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/user")
    public Mono<String> user(){
        // how to get loggedIn user
//        return SecurityContextHolder.getContext().getAuthentication().getName(); // non-reactive way
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName);
    }


    //another way to get the loggedIn user.
    @GetMapping("/principal")
    public Mono<String> principal(@AuthenticationPrincipal Mono<Authentication> user){
        return user.map(Authentication::getName);
    }


    /**
     * The difference between the above, both get user data from security context holder
     * The first one : can be used any ware
     * The second one: only used inside beans and should be injected as a parameter
     */
}
