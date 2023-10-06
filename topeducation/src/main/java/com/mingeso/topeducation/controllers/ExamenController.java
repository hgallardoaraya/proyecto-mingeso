package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.responses.Response;
import com.mingeso.topeducation.services.ExamenService;
import lombok.RequiredArgsConstructor;
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
import java.util.Date;

@Controller
@RequestMapping("/examenes")
@RequiredArgsConstructor
public class ExamenController {
    private final ExamenService examenService;

    @GetMapping("/importar")
    public String ingresar(){
        return "importar-examen.html";
    }

    @PostMapping(value = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importarExamen(@RequestParam MultipartFile archivo) throws IOException {
        System.out.println(archivo.getOriginalFilename());
        examenService.importarExamen(archivo);

        Response response = new Response(HttpStatus.CREATED.value(),
                "Estudiante ingresado correctamente!",
                "/estudiantes/exito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
