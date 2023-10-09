package com.mingeso.topeducation.exceptions;

import com.mingeso.topeducation.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(EstudianteNoExisteException.class)
    public ResponseEntity<Response> handleEstudianteNoExisteException(EstudianteNoExisteException ex){
        Response response = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoColegioNoExisteException.class)
    public ResponseEntity<Response> handleTipoColegioNoExisteException(TipoColegioNoExisteException ex){
        Response response = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoPagoArancelNoExisteException.class)
    public ResponseEntity<Response> handleTipoPagoArancelNoExisteException(TipoPagoArancelNoExisteException ex){
        Response response = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InteresMesesAtrasoNoExisteException.class)
    public ResponseEntity<Response> handleInteresMesesAtrasoNoExisteException(InteresMesesAtrasoNoExisteException ex){
        Response response = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
