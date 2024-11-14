package com.husseinelbhrawy.MovieAPI.Payload;

import lombok.Data;

@Data
public class UploadFileResponse {
    private int statusCode;
    private  String message;
    private  String url;
}
