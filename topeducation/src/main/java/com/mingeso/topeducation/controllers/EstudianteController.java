package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.SaveEstudianteRequest;
import com.mingeso.topeducation.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteService estudianteService;

    @PostMapping("/crear")
    public ResponseEntity<?> saveEstudiante(@RequestBody SaveEstudianteRequest request){
        estudianteService.saveEstudiante(request);
        return new ResponseEntity<>("Estudiante ingresado", HttpStatus.OK);
    }

}
