package com.example.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user1 = User.withUsername(username).password("12345").authorities("read").build();
        UserDetails user2 = User.withUsername(username).password("12345").authorities("read").build();
        UserDetails user3 = User.withUsername(username).password("12345").authorities("read").build();

        return user1;
    }
}
