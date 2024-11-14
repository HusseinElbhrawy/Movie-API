package com.husseinelbhrawy.MovieAPI.Auth.DTO;

import lombok.*;

@Builder
public record OTPRequest ( String email , int otp) {
}

//@Data
//@NoArgsConstructor
//
//@AllArgsConstructor
//@Builder
//public class OTPRequest {
//    private String email ;
//    private int otp;
//}