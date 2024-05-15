package com.github.angel.raa.configuration;

import com.github.angel.raa.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeansInjector {
    private final CustomUserDetailsService detailsService;
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @SneakyThrows
    @Bean
    AuthenticationManager authenticationManager (@NotNull AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider provider (){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(detailsService);
        return provider;

    }
}
