package com.mingeso.topeducation_ms1.controllers;

import com.mingeso.topeducation_ms1.dtos.EstudianteDTO;
import com.mingeso.topeducation_ms1.dtos.EstudianteResponse;
import com.mingeso.topeducation_ms1.dtos.IngresarEstudianteDTO;
import com.mingeso.topeducation_ms1.dtos.Response;
import com.mingeso.topeducation_ms1.entities.Estudiante;
import com.mingeso.topeducation_ms1.services.EstudianteService;
import com.mingeso.topeducation_ms1.utils.Mapper;
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

    @RequestMapping(params="rut",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstudianteResponse> obtenerEstudiantePorRut(@RequestParam String rut) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorRut(rut);
        return new ResponseEntity<>(
                new EstudianteResponse(
                        HttpStatus.OK.value(),
                        "Estudiante obtenido correctamente.",
                        Mapper.estudianteToEstudianteDTO(estudiante)
                ),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> obtenerDatosEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerDatosEstudiantes();
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.OK.value(),
                        "Estudiantes obtenidos correctamente.",
                        Mapper.estudiantesToEstudiantesDTOS(estudiantes)
                ),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/cuotasPactadas",
            params = {"idEstudiante", "cuotasPactadas"},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstudianteResponse> actualizarCuotasPactadas(@RequestParam Integer idEstudiante,
                                                             @RequestParam Integer cuotasPactadas) {
        Estudiante estudiante = estudianteService.actualizarCuotasPactadas(idEstudiante, cuotasPactadas);
        return new ResponseEntity<>(
                new EstudianteResponse(
                        HttpStatus.OK.value(),
                        "Las cuotas pactadas han sido actualizadas.",
                        Mapper.estudianteToEstudianteDTO(estudiante)
                ),
                HttpStatus.OK);
    }
}
