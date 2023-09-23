package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.requests.SaveEstudianteRequest;
import com.mingeso.topeducation.requests.SubirExamenRequest;
import com.mingeso.topeducation.responses.Response;
import com.mingeso.topeducation.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public ResponseEntity<?> saveEstudiante(@RequestBody SaveEstudianteRequest request) {
        estudianteService.saveEstudiante(request);
        Response response = new Response(HttpStatus.CREATED.value(),
                "Estudiante ingresado correctamente!",
                "/estudiantes/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/maxcuotas")
    public ResponseEntity<?> getMaxCuotasByRut(@RequestParam("rut") String rut){
        Integer maxCuotas = estudianteService.getMaxCuotasByRut(rut);
        Map<String, Integer> responseData = new HashMap<>();
        responseData.put("maxCuotas", maxCuotas);
        Response response = new Response(HttpStatus.OK.value(),
                "Consulta realizada con éxito!",
                responseData
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/examen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importarExamen(@RequestParam String rut, @RequestParam MultipartFile archivo) throws IOException {
        System.out.println(archivo.getOriginalFilename());
        System.out.println(rut);

        Response response = new Response(HttpStatus.CREATED.value(),
                "Estudiante ingresado correctamente!",
                "/estudiantes/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
