package com.husseinelbhrawy.MovieAPI.Auth.Controller;

import com.husseinelbhrawy.MovieAPI.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.RegisterRequest;

import com.husseinelbhrawy.MovieAPI.Auth.Entity.RefreshToken;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import com.husseinelbhrawy.MovieAPI.Auth.Security.JWTTokenProvider;
import com.husseinelbhrawy.MovieAPI.Auth.Security.RefreshTokenServices;
import com.husseinelbhrawy.MovieAPI.Auth.Services.Base.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private  final RefreshTokenServices refreshTokenServices;
    private  final JWTTokenProvider jwtServices;

    @PostMapping(value = {"/login" , "/signin"})  //? use /login or /signin
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginParam){

        return  new ResponseEntity<>( authService.login(loginParam), HttpStatus.OK);

    }


    @PostMapping(value = {"/register" , "/signup"})  //? use /register or /signup
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerParam){
        return  new ResponseEntity<>(authService.register(registerParam), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public  ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshToken refreshToken){
        RefreshToken refreshTokenVerified =  refreshTokenServices.verifyRefreshToken(refreshToken.getRefreshToken());
        User user = refreshTokenVerified.getUser();


        String token = jwtServices.generateToken(user);
        AuthResponse authResponse =  AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenVerified.getRefreshToken())
                .build();

        return  new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

}
