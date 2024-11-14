package com.husseinelbhrawy.MovieAPI.Auth.DTO;

import lombok.*;

@Builder
public record OTPRequest ( String email , int otp) {
}
