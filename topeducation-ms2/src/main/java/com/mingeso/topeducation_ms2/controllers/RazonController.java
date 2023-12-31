package com.mingeso.topeducation_ms2.controllers;


import com.mingeso.topeducation_ms2.dtos.razones.ActualizarEstadoRazonDTO;
import com.mingeso.topeducation_ms2.dtos.razones.RazonResponse;
import com.mingeso.topeducation_ms2.dtos.razones.RazonesResponse;
import com.mingeso.topeducation_ms2.entities.Razon;
import com.mingeso.topeducation_ms2.services.RazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/razones")
public class RazonController {
    private final RazonService razonService;

    @Autowired
    public RazonController(RazonService razonService){
        this.razonService = razonService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RazonesResponse> obtenerRazones(
            @RequestParam(value = "estados", required = false) Integer[] estados,
            @RequestParam(value = "estudiantes", required = false) Integer[] estudiantes,
            @RequestParam(value = "tipos", required = false) Integer[] tipos
    ){
        List<Razon> razones = razonService.obtenerRazones(estados, estudiantes, tipos);

        return new ResponseEntity<>(
                new RazonesResponse(
                        HttpStatus.OK.value(),
                        "Razones obtenidas correctamente",
                        razones
                ),
                HttpStatus.OK
        );
    }
    @RequestMapping(
            value = "/estudiantes/{rut}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RazonesResponse> obtenerRazonesPorRut(
            @PathVariable(value = "rut") String rut,
            @RequestParam(value = "estados", required = false) Integer[] estados,
            @RequestParam(value = "tipos", required = false) Integer[] tipos
            ){
        List<Razon> razones = razonService.obtenerRazonesPorRut(rut, estados, tipos);

        return new ResponseEntity<>(
                new RazonesResponse(
                        HttpStatus.OK.value(),
                        "Razones obtenidas correctamente",
                        razones
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<RazonesResponse> generarCuotas(@RequestParam String rut,
                                                         @RequestParam Integer numCuotas
    ){
        List<Razon> razones = razonService.generarCuotas(rut, numCuotas);

        return new ResponseEntity<>(
                new RazonesResponse(
                        HttpStatus.CREATED.value(),
                        "Razones generadas correctamente.",
                        razones
                ),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RazonesResponse> actualizarRazones(@RequestBody List<Razon> razones){
        List<Razon> razonesResultantes = razonService.actualizarRazones(razones);

        return new ResponseEntity<>(
                new RazonesResponse(
                        HttpStatus.CREATED.value(),
                        "Razones generadas correctamente.",
                        razonesResultantes
                ),
                HttpStatus.CREATED);
    }

    @PutMapping("/{idRazon}/estado/{idEstadoRazon}")
    public ResponseEntity<RazonResponse> actualizarEstadoRazon(@PathVariable Integer idRazon, @PathVariable Integer idEstadoRazon){

        Razon razonResultante = razonService.actualizarEstadoRazon(idRazon, idEstadoRazon);

        return new ResponseEntity<>(
                new RazonResponse(
                        HttpStatus.CREATED.value(),
                        "Estado de la razón actualizado correctamente.",
                        razonResultante
                ),
                HttpStatus.CREATED);
    }
}
