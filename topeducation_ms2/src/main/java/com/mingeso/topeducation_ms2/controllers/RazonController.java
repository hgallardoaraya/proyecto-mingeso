package com.mingeso.topeducation_ms2.controllers;


import com.mingeso.topeducation_ms2.dtos.RazonesResponse;
import com.mingeso.topeducation_ms2.dtos.Response;
import com.mingeso.topeducation_ms2.entities.Razon;
import com.mingeso.topeducation_ms2.services.RazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/razones")
public class RazonController {
    private final RazonService razonService;

    @Autowired
    public RazonController(RazonService razonService){
        this.razonService = razonService;
    }

    @PostMapping
    public ResponseEntity<RazonesResponse> generarCuotas(@RequestParam String rut,
                                                         @RequestParam Integer numCuotas
    ){
        List<Razon> razones = razonService.generarCuotas(rut, numCuotas);

        return new ResponseEntity<>(
                new RazonesResponse(
                        HttpStatus.CREATED.value(),
                        "Razones generadas correctamente.",
                        razones
                ),
                HttpStatus.CREATED);
    }
}
