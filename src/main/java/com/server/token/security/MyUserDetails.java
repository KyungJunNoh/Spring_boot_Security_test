package com.server.token.security;

import com.server.token.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        final UserDetails user = userRepository.findByUserEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + userEmail + "' not found");
        }

        return user;
    }
}
