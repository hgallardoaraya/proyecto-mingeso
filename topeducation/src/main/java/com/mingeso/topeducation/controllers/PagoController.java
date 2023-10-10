package com.mingeso.topeducation.controllers;

import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.requests.RegistrarPagoRequest;
import com.mingeso.topeducation.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;
    private final LocalDate fechaActual;

    @Autowired
    public PagoController(PagoService pagoService){
        this.fechaActual = LocalDate.now();
        this.pagoService = pagoService;
    }

    @GetMapping("/registrar/ingresar-estudiante")
    public String vistaIngresarEstudiantePago(){
        if(fechaActual.getDayOfMonth() < 5 || fechaActual.getDayOfMonth() > 10) return "error-fechas-pago.html";
        return "registrar-pago-ingreso-estudiante.html";
    }

    @GetMapping("/registrar")
    public String vistaSeleccionarRazonesPago(@RequestParam String rut, Model model){
        if(fechaActual.getDayOfMonth() < 5 || fechaActual.getDayOfMonth() > 10) return "error-fechas-pago.html";
        ArrayList<Razon> razones = pagoService.obtenerRazonesAPagar(rut);
        model.addAttribute("razones", razones);
        model.addAttribute("rut", rut);
        return "registrar-pago-seleccionar-razones.html";
    }

    @PostMapping("/registrar")
    public String registrarPago(@RequestBody RegistrarPagoRequest request){
        if(fechaActual.getDayOfMonth() < 5 || fechaActual.getDayOfMonth() > 10) return "error-fechas-pago.html";
        pagoService.registrarPago(request);
        return "redirect:/registrar/ingresar-estudiante";
    }

    @GetMapping("/reporte")
    public String calcularReporteResumen(Model model){
        model.addAttribute("reporte", pagoService.calcularReporteResumen());
        return "reporte-resumen.html";
    }
}
