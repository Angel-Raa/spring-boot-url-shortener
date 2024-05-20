package com.github.angel.raa.service.impl;

import com.github.angel.raa.dto.*;
import com.github.angel.raa.exception.InvalidPasswordException;
import com.github.angel.raa.exception.InvalidTokenException;
import com.github.angel.raa.exception.NotFoundUsername;
import com.github.angel.raa.persistence.entity.*;
import com.github.angel.raa.persistence.repository.CredentialRepository;
import com.github.angel.raa.persistence.repository.RoleRepository;
import com.github.angel.raa.persistence.repository.TokenRepository;
import com.github.angel.raa.persistence.repository.UserRepository;
import com.github.angel.raa.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService service;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;

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
        save(user, accessToken,service.extractExpiration(accessToken));
        save(user,refreshToken, service.extractExpiration(refreshToken));
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

    @Transactional
    @Override
    public Response<AuthenticateResponse> refreshToken(@NotNull RefreshTokenRequestDTO refreshToken) {
        String refresh = refreshToken.refreshToken();

        // Validate the refresh token
        if(!service.isRefreshTokenValid(refresh)){
            return Response.<AuthenticateResponse>builder()
                    .message("Invalid refresh token")
                    .timestamp(now())
                    .status(UNAUTHORIZED)
                    .error(true)
                    .code(401)
                    .build();
        }
        // Retrieve the token entity from the database

        TokenEntity tokenEntity = tokenRepository.findByToken(refresh).orElseThrow(() -> new InvalidTokenException("Invalid Token", true, BAD_REQUEST, now()));
        // Check if the token is expired
        if(tokenEntity.getExpiration().before(new Date())){
            tokenRepository.delete(tokenEntity);
            return Response.<AuthenticateResponse>builder()
                    .message("Refresh token expired")
                    .timestamp(now())
                    .code(401)
                    .error(true)
                    .status(UNAUTHORIZED)
                    .build();
        }
        // Generate a new access token
        UserEntity user = tokenEntity.getUser();
        String newAccess = service.generateAccessToken(user);
        String newRefresh = service.generateRefreshToken(user);
        AuthenticateResponse response = new AuthenticateResponse(newAccess, newRefresh);
        return Response.<AuthenticateResponse>builder()
                .message("Token refreshed successfully")
                .data(response)
                .timestamp(now())
                .error(false)
                .code(200)
                .status(OK)
                .build();
    }
    @Transactional
    @Override
    public Response<AuthenticateResponse> logout(@NotNull RefreshTokenRequestDTO refreshToken) {
        String refresh = refreshToken.refreshToken();
        TokenEntity tokenEntity = tokenRepository.findByToken(refresh).orElseThrow(() -> new InvalidTokenException("Invalid Token", true, BAD_REQUEST, now()));
        tokenRepository.delete(tokenEntity);
        return Response.<AuthenticateResponse>builder()
                .message("Logged out successfully")
                .timestamp(now())
                .code(200)
                .error(false)
                .status(OK)
                .build();
    }
    @Transactional
    @Override
    public Response<AuthenticateResponse> register(@NotNull Register register) {
        System.out.println(register);
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
        CredentialEntity credential = new CredentialEntity();
        credential.setPassword(bCryptPasswordEncoder.encode(password));
        RoleEntity role = new RoleEntity();
        UserEntity user = new UserEntity();
        // Guardar el rol primero
        role.setAuthorities(Authorities.ROLE_USER);
        roleRepository.save(role);

        // Guardar el usuario sin credenciales
        user.setEmail(email);
        user.setUsername(username);
        user.setRole(role);

        // Establecer la relación de credenciales con el usuario y guardar credenciales
        credential.setUser(user);
        credentialRepository.save(credential);

        user.setCredential(credential);

        userRepository.save(user);
        String accessToken = service.generateAccessToken(user);
        String refreshToken = service.generateRefreshToken(user);
        save(user, accessToken,service.extractExpiration(accessToken));
        save(user,refreshToken, service.extractExpiration(refreshToken));
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

    private void save(UserEntity user, String token, Date expiration){
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenEntity.setExpiration(expiration);
        tokenRepository.save(tokenEntity);
    }

}
