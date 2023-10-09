package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.IngresarEstudianteRequest;
import com.mingeso.topeducation.responses.Response;
import com.mingeso.topeducation.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteService estudianteService;
    @GetMapping("/ingresar")
    public String ingresar(){
        return "ingresar-estudiante.html";
    }

    @GetMapping("/exito")
    public String exito(){
        return "exito.html";
    }

    @PostMapping
    public ResponseEntity<Response> ingresarEstudiante(@RequestBody IngresarEstudianteRequest request) {
        return estudianteService.ingresarEstudiante(request);
    }

    @GetMapping("/maxcuotas")
    public ResponseEntity<?> getMaxCuotasByRut(@RequestParam("rut") String rut){
        return estudianteService.getMaxCuotasByRut(rut);
    }

}
