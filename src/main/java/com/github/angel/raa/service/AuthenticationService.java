package com.github.angel.raa.service;

import com.github.angel.raa.dto.*;
import com.github.angel.raa.persistence.entity.UserEntity;

public interface AuthenticationService {
    Response<AuthenticateResponse> authenticate(Login login);
    Response<AuthenticateResponse> refreshToken(RefreshTokenRequestDTO refreshToken);
    Response<AuthenticateResponse> logout(RefreshTokenRequestDTO refreshToken);
    Response<AuthenticateResponse> register(Register register);
    void  validatePassword (String password);
    UserEntity getAuthenticatedUser ();

}
