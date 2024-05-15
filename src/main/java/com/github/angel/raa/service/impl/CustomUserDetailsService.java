package com.github.angel.raa.service.impl;

import com.github.angel.raa.exception.NotFoundUsername;
import com.github.angel.raa.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundUsername(
                "Sorry, we couldn't find the user you're looking for. Please double-check the entered information and try again.",
                false,
                NOT_FOUND,
                now()
        ));
    }
}
