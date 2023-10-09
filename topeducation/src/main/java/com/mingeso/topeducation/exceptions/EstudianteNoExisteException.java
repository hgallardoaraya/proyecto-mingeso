package com.mingeso.topeducation.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

public class EstudianteNoExisteException extends IllegalArgumentException{
    public EstudianteNoExisteException(String rut){
        super("El estudiante con rut " + rut + " no existe.");
    }

    public EstudianteNoExisteException(Integer id){
        super("El estudiante " + id + " no existe.");
    }
}
