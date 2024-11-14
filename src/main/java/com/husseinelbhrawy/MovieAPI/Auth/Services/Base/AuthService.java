package com.husseinelbhrawy.MovieAPI.Auth.Services.Base;

import com.husseinelbhrawy.MovieAPI.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest request);

}
