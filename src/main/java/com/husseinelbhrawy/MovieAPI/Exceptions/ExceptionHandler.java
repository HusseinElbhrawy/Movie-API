package com.husseinelbhrawy.MovieAPI.Exceptions;

import com.husseinelbhrawy.MovieAPI.Payload.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGeneralException(Exception e , WebRequest request){
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR  ).body(CustomErrorResponse.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(e.getMessage())
                        .details(request.getDescription(false))
                        .timestamp(new Date())
                .build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleMovieNotFoundException(MovieNotFoundException e , WebRequest request){
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR  ).body(CustomErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .details(request.getDescription(false))
                .timestamp(new Date())
                .build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        Map<String , String> errors = new HashMap<>();

        for (ObjectError allError : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) allError).getField();
            String message =allError.getDefaultMessage() ;

            errors.put(fieldName , message);
        }
        return  new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }


}
