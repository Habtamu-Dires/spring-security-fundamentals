package com.example.services;

import com.example.repository.UserRepository;
import com.example.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service   // this will be added to bean and represent UserDetailService implementation
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        var user = userRepository.findUserByUsername(username);

        return user.map(SecurityUser::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User name not found " + username));
    }
}
