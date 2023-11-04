package com.mingeso.topeducation_ms1.controllers;

import com.mingeso.topeducation_ms1.dtos.EstudianteDTO;
import com.mingeso.topeducation_ms1.dtos.IngresarEstudianteDTO;
import com.mingeso.topeducation_ms1.dtos.Response;
import com.mingeso.topeducation_ms1.entities.Estudiante;
import com.mingeso.topeducation_ms1.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    private final EstudianteService estudianteService;
    @Autowired
    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }

    @PostMapping
    public ResponseEntity<Response> ingresarEstudiante(@RequestBody IngresarEstudianteDTO estudianteDTO) {
        Estudiante estudiante = estudianteService.ingresarEstudiante(estudianteDTO);
        Map<String, Estudiante> data = new HashMap<>();
        data.put("estudiante", estudiante);
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.CREATED.value(),
                        "Estudiante ingresado correctamente.",
                        "/estudiantes/ingresar",
                        data
                ),
                HttpStatus.CREATED);
    }

    @RequestMapping(params="rut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> obtenerEstudiantePorRut(@RequestParam String rut) {
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorRut(rut);
        Map<String, EstudianteDTO> data = new HashMap<>();
        data.put("estudiante", estudiante);
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.OK.value(),
                        "Estudiante obtenido correctamente.",
                        data
                ),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> obtenerDatosEstudiantes() {
        List<EstudianteDTO> estudiantesDTOS = estudianteService.obtenerDatosEstudiantes();
        System.out.println("Estudiantes DTOS " + estudiantesDTOS.toString());
        Map<String, List<EstudianteDTO>> data = new HashMap<>();
        data.put("estudiantes", estudiantesDTOS);
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.OK.value(),
                        "Estudiantes obtenidos correctamente.",
                        data
                ),
                HttpStatus.OK);
    }
}
