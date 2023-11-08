package com.mingeso.topeducation_ms3.controllers;

import com.mingeso.topeducation_ms3.dtos.Response;
import com.mingeso.topeducation_ms3.services.CalculosAdministrativosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/calculos")
public class CalculosAdministrativosController {
    CalculosAdministrativosService calculosAdministrativosService;

    @Autowired
    public CalculosAdministrativosController(CalculosAdministrativosService calculosAdministrativosService){
        this.calculosAdministrativosService = calculosAdministrativosService;
    }

    @PostMapping("/planilla")
    public ResponseEntity<Response> calcularPlanilla(){
        calculosAdministrativosService.calcularPlanilla();

        return new ResponseEntity<>(new Response(
                HttpStatus.OK.value(),
                "La planilla ha sido calculada con éxito."
        ),
                HttpStatus.OK);
    }

//    @GetMapping("/reporte")
//    public String calcularReporteResumen(Model model){
////        model.addAttribute("reporte", pagoService.calcularReporteResumen());
////        return "reporte-resumen.html";
//    }

}
