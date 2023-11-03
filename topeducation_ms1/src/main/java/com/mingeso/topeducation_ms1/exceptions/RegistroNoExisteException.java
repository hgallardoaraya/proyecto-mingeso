package com.mingeso.topeducation_ms1.exceptions;


public class RegistroNoExisteException extends IllegalArgumentException{
    public RegistroNoExisteException(String message){
        super(message);
    }
}
