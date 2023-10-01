package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.RegistrarPagoRequest;
import com.mingeso.topeducation.services.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping("/registrar")
    public String vistaRegistrarPago(){
        return "registrar-pago.html";
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPago(@RequestBody RegistrarPagoRequest request){
        pagoService.registrarPago(request);
        return new ResponseEntity<>("pago realizado con exito", HttpStatus.OK);
    }

}
