package com.mingeso.topeducation_ms2.controllers;


import com.mingeso.topeducation_ms2.dtos.ApiResponse;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/razones")
public class RazonController {
    private final RazonService razonService;

    @Autowired
    public RazonController(RazonService razonService){
        this.razonService = razonService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> generarCuotas(@RequestParam String rut,
                                           @RequestParam Integer numCuotas
    ){
        List<Razon> razones = razonService.generarCuotas(rut, numCuotas);

        Map<String, List<Razon>> data = new HashMap<>();
        data.put("razones", razones);
        return new ResponseEntity<>(
                new ApiResponse(
                        HttpStatus.CREATED.value(),
                        "Razones generadas correctamente.",
                        data
                ),
                HttpStatus.CREATED);
    }
}
