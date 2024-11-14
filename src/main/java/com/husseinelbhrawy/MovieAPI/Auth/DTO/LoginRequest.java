package com.husseinelbhrawy.MovieAPI.Auth.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private  String username;
    private  String password;
}

