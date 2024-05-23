package com.github.angel.raa.configuration;

import com.github.angel.raa.exception.InvalidTokenException;
import com.github.angel.raa.service.impl.CustomUserDetailsService;
import com.github.angel.raa.service.impl.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.time.LocalDateTime.now;


@Component

public class AuthenticationFilter extends BasicAuthenticationFilter {
    private final JwtService service;
    private final CustomUserDetailsService detailsService;


    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtService service, CustomUserDetailsService detailsService) {
        super(authenticationManager);
        this.service = service;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
       String header = request.getHeader("Authorization");
       if(header == null || !header.startsWith("Bearer ")){
           chain.doFilter(request, response);
           return;
       }

       String token = header.substring(7);
       UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    @Contract("_ -> new")
    private @NotNull UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = service.extractUsername(token);
        UserDetails userDetails = detailsService.loadUserByUsername(username);
        if (service.isRefreshTokenValid(token) || service.validateToken(token, userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        throw new InvalidTokenException("Invalid token", true, HttpStatus.BAD_REQUEST, now());
    }

}
