package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.services.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<?> registrarPago(@RequestParam String rut, @RequestParam Integer[] idsRazones){
        pagoService.registrarPago(rut, idsRazones);
        return new ResponseEntity<>("pago realizado con exito", HttpStatus.OK);
    }

}
