package com.mingeso.topeducation_ms2.exceptions;


public class RegistroNoExisteException extends IllegalArgumentException{
    public RegistroNoExisteException(String message){
        super(message);
    }
}
