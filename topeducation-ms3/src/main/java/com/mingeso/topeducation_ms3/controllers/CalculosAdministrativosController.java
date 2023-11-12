package com.mingeso.topeducation_ms3.controllers;

import com.mingeso.topeducation_ms3.dtos.Response;
import com.mingeso.topeducation_ms3.dtos.razon.RazonDTO;
import com.mingeso.topeducation_ms3.dtos.razon.RazonesResponse;
import com.mingeso.topeducation_ms3.dtos.reporte.EntradaReporteResumen;
import com.mingeso.topeducation_ms3.dtos.reporte.ReporteResponse;
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
import java.util.List;

@RestController
@RequestMapping("/calculos")
public class CalculosAdministrativosController {
    CalculosAdministrativosService calculosAdministrativosService;

    @Autowired
    public CalculosAdministrativosController(CalculosAdministrativosService calculosAdministrativosService){
        this.calculosAdministrativosService = calculosAdministrativosService;
    }

    @PostMapping("/planilla")
    public ResponseEntity<RazonesResponse> calcularPlanilla(){
        List<RazonDTO> razonesResultantes = calculosAdministrativosService.calcularPlanilla();

        return new ResponseEntity<>(new RazonesResponse(
            HttpStatus.OK.value(),
            "La planilla ha sido calculada con éxito.",
                razonesResultantes
        ),
            HttpStatus.OK);
    }

    @GetMapping("/reporte")
    public ResponseEntity<ReporteResponse> calcularReporteResumen(Model model){
        List<EntradaReporteResumen> reporte = calculosAdministrativosService.calcularReporteResumen();

        return new ResponseEntity<>(new ReporteResponse(
            HttpStatus.OK.value(),
            "Reporte resumen calculado con éxito.",
            reporte
        ),
            HttpStatus.OK);
    }
}
