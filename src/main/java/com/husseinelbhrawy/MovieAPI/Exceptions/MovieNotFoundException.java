package com.husseinelbhrawy.MovieAPI.Exceptions;

public class MovieNotFoundException extends  RuntimeException{
    public MovieNotFoundException(String message) {
        super(message);
    }
}
