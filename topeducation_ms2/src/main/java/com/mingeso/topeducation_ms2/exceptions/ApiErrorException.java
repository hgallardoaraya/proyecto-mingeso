package com.mingeso.topeducation_ms2.exceptions;

public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String message){
        super(message);
    }
}
