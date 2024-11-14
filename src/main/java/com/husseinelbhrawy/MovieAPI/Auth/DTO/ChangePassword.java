package com.husseinelbhrawy.MovieAPI.Auth.DTO;

import jakarta.validation.constraints.Size;

public record ChangePassword(String email,
                             @Size(min = 6 , message = "Password must be at least 6 characters" ) String password ,
                             @Size(min = 6 , message = "Confirm Password must be at least 6 characters" )  String confirmPassword  ) {
}
