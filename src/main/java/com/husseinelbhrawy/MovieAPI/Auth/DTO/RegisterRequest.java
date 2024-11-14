package com.husseinelbhrawy.MovieAPI.Auth.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    private  String name;
    private  String username;
    private  String email;
    private  String password;
}
