package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.services.PagoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @PostMapping("/registrar")
    public String ingresar(){
        return "importar-examen.html";
    }

}
