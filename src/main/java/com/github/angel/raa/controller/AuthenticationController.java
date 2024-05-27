package com.github.angel.raa.controller;

import com.github.angel.raa.dto.*;
import com.github.angel.raa.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/api/authentication")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/login")
    @PreAuthorize("permitAll")
    public ResponseEntity<Response<AuthenticateResponse>> login(@Valid @RequestBody Login login){
        Response<AuthenticateResponse> response = service.authenticate(login);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    @PreAuthorize("permitAll")
    public ResponseEntity<Response<AuthenticateResponse>> register(@Valid @RequestBody Register register){
        Response<AuthenticateResponse> response = service.register(register);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    @PreAuthorize("permitAll")
    public ResponseEntity<Response<AuthenticateResponse>> refresh( @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        var res = service.refreshToken(refreshTokenRequestDTO);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/logout")
    @PreAuthorize("permitAll")
    public ResponseEntity<Response<AuthenticateResponse>> logout (@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        var res = service.logout(refreshTokenRequestDTO);
        return ResponseEntity.ok(res);
    }

}
