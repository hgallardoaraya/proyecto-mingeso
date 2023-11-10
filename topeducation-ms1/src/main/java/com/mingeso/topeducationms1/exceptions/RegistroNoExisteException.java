package com.mingeso.topeducationms1.exceptions;


public class RegistroNoExisteException extends IllegalArgumentException{
    public RegistroNoExisteException(String message){
        super(message);
    }
}
