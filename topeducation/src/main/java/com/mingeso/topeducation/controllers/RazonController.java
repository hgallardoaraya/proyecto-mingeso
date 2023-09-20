package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.GenerarRazonesRequest;
import com.mingeso.topeducation.responses.ApiResponse;
import com.mingeso.topeducation.services.RazonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/razones")
@RequiredArgsConstructor
public class RazonController {
    private final RazonService razonService;

//    @GetMapping("/cuotas/generar")
//    public String generarCuotas(){
//        return "ingresar-cuotas.html";
//    }

    @PostMapping("/generar")
    public ResponseEntity<?> generarRazones(@RequestParam String rut){
        razonService.generarRazonesEstudiante(rut);
        ApiResponse response = new ApiResponse(HttpStatus.CREATED.value(), "Cuotas generadas correcamente", "/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
