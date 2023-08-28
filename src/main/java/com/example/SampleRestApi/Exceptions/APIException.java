package com.example.SampleRestApi.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class APIException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public APIException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }
}
