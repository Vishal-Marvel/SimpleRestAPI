package com.example.SampleRestApi.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConstraintException extends RuntimeException{
    public ConstraintException(String message) {
        super(message);
    }
}
