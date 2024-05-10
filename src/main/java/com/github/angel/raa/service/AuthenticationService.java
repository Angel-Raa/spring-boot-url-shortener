package com.github.angel.raa.service;

import com.github.angel.raa.dto.*;

public interface AuthenticationService {
    Response<AuthenticateResponse> authenticate(Login login);
    Response<AuthenticateResponse> refreshToken(RefreshTokenRequestDTO refreshToken);
    Response<AuthenticateResponse> logout(RefreshTokenRequestDTO refreshToken);
    Response<AuthenticateResponse> register(Register login);
}
