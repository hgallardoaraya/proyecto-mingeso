package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.SaveEstudianteRequest;
import com.mingeso.topeducation.responses.ApiResponse;
import com.mingeso.topeducation.services.EstudianteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public ResponseEntity<?> saveEstudiante(@RequestBody SaveEstudianteRequest request) {
        estudianteService.saveEstudiante(request);
        ApiResponse response = new ApiResponse(HttpStatus.CREATED.value(),
                "Estudiante ingresado correctamente!",
                "/estudiantes/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
