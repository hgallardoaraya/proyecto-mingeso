package com.mingeso.topeducation.exceptions;


import org.springframework.http.HttpStatus;

public class TipoColegioNoExisteException extends IllegalArgumentException{
    public TipoColegioNoExisteException(String message){
        super(message);
    }
}
