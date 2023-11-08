package com.mingeso.topeducation_ms3.controllers;

import com.mingeso.topeducation_ms3.dtos.Response;
import com.mingeso.topeducation_ms3.services.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/examenes")
public class ExamenController {
    private final ExamenService examenService;

    @Autowired
    public ExamenController(ExamenService examenService){
        this.examenService = examenService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importarExamen(@RequestParam MultipartFile archivo) throws IOException {
        examenService.importarExamen(archivo, LocalDate.now());

        return new ResponseEntity<>(
                new Response(
                        HttpStatus.CREATED.value(),
                        "Examen importado con éxito."),
                HttpStatus.CREATED);
    }
}
