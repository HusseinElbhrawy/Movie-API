package com.husseinelbhrawy.MovieAPI.Auth.DTO;


import lombok.Builder;

@Builder
public record MailBody(String to ,  String subject , String body){}
