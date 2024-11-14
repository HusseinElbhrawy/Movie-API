package com.husseinelbhrawy.MovieAPI.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomErrorResponse {
    private  int statusCode;
    private String message;
    private String details;
    private Date timestamp;
}
