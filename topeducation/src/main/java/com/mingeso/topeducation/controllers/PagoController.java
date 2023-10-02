package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.requests.RegistrarPagoRequest;
import com.mingeso.topeducation.responses.Response;
import com.mingeso.topeducation.services.PagoService;
import com.mingeso.topeducation.services.RazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService, RazonService razonService){
        this.pagoService = pagoService;
    }

    @GetMapping("/registrar/ingresar-estudiante")
    public String vistaIngresarEstudiantePago(){
        return "registrar-pago-ingreso-estudiante.html";
    }
    @GetMapping("/registrar")
    public String vistaSeleccionarRazonesPago(@RequestParam String rut, Model model){
        ArrayList<Razon> razones = pagoService.obtenerRazonesPendientesDePago(rut);
        model.addAttribute("razones", razones);
        model.addAttribute("rut", rut);
        return "registrar-pago-seleccionar-razones.html";
    }

    @PostMapping("/registrar")
    public String registrarPago(@RequestBody RegistrarPagoRequest request){
        pagoService.registrarPago(request);
        return "redirect:/registrar/ingresar-estudiante";
    }

}
