package com.husseinelbhrawy.MovieAPI.Auth.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.Roles;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private  String name;
    private  String username;
    private  String email;
    private  String accessToken;
    private  String refreshToken;
    private Roles roles;
}
