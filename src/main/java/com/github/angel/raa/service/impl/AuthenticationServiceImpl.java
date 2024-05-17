package com.github.angel.raa.service.impl;

import com.github.angel.raa.dto.*;
import com.github.angel.raa.exception.InvalidPasswordException;
import com.github.angel.raa.exception.NotFoundUsername;
import com.github.angel.raa.persistence.entity.Authorities;
import com.github.angel.raa.persistence.entity.CredentialEntity;
import com.github.angel.raa.persistence.entity.RoleEntity;
import com.github.angel.raa.persistence.entity.UserEntity;
import com.github.angel.raa.persistence.repository.TokenRepository;
import com.github.angel.raa.persistence.repository.UserRepository;
import com.github.angel.raa.service.AuthenticationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService service;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository, JwtService service) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.service = service;
    }

    @Transactional
    @Override
    public Response<AuthenticateResponse> authenticate(@NotNull Login login) {
        String username = login.username();
        String password = login.password();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundUsername(
                "Sorry, we couldn't find the user you're looking for. Please double-check the entered information and try again.",
                false,
                NOT_FOUND,
                now()
        ));
        authenticationManager.authenticate(authentication);
        String accessToken = service.generateAccessToken(user);
        String refreshToken = service.generateRefreshToken(user);
        AuthenticateResponse response = new AuthenticateResponse(accessToken, refreshToken);
        return Response.<AuthenticateResponse>builder()
                .message("Authentication successful")
                .data(response)
                .code(200)
                .timestamp(now())
                .status(OK)
                .error(false)
                .build();
    }

    @Override
    public Response<AuthenticateResponse> refreshToken(RefreshTokenRequestDTO refreshToken) {
        return null;
    }

    @Override
    public Response<AuthenticateResponse> logout(RefreshTokenRequestDTO refreshToken) {
        return null;
    }
    @Transactional
    @Override
    public Response<AuthenticateResponse> register(Register register) {
        String username = register.username();
        String email = register.email();
        String password = register.password();
        // Check if username or email already exists
        if(userRepository.existsByUsername(username) || userRepository.existsByEmail(email)){
            return Response.<AuthenticateResponse>builder()
                    .message("Username or email already exists")
                    .code(400)
                    .timestamp(now())
                    .status(BAD_REQUEST)
                    .build();
        }
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUsername(username);
        user.setRole(new RoleEntity(Authorities.ROLE_USER));
        user.setCredential(new CredentialEntity(bCryptPasswordEncoder.encode(password), user));
        userRepository.save(user);
        String accessToken = service.generateAccessToken(user);
        String refreshToken = service.generateRefreshToken(user);
        AuthenticateResponse response = new AuthenticateResponse(accessToken, refreshToken);
        return Response.<AuthenticateResponse>builder()
                .message("Registration successful")
                .data(response)
                .code(201)
                .timestamp(now())
                .status(CREATED)
                .error(false)
                .build();
    }

    @Override
    public void validatePassword(String password) {
        if(!StringUtils.hasText(password)  || !StringUtils.hasText(password)){
            throw new InvalidPasswordException("Passwords do not match");
        }
        if(!password.equals(password)){
            throw new InvalidPasswordException("Passwords do not match");
        }
    }
}
