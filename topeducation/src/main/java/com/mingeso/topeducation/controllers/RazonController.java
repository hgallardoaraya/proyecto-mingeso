package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.responses.Response;
import com.mingeso.topeducation.services.RazonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/razones")
@RequiredArgsConstructor
public class RazonController {
    private final RazonService razonService;

    @GetMapping("/generar")
    public String generarCuotas(){
        return "generar-cuotas.html";
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generarCuotas(@RequestParam String rut,
                                           @RequestParam Integer numCuotas
                                           ){
        razonService.generarCuotas(rut, numCuotas);
        Response response = new Response(HttpStatus.CREATED.value(), "Cuotas generadas correcamente", "/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
