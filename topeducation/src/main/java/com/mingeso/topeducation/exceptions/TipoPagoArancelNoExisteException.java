package com.mingeso.topeducation.exceptions;

public class TipoPagoArancelNoExisteException extends IllegalArgumentException
{
    public TipoPagoArancelNoExisteException(String message){
        super(message);
    }
}
