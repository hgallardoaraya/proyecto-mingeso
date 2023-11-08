package com.mingeso.topeducation_ms2.controllers;

import com.mingeso.topeducation_ms2.dtos.pagos.PagoResponse;
import com.mingeso.topeducation_ms2.dtos.pagos.PagosResponse;
import com.mingeso.topeducation_ms2.dtos.pagos.RegistrarPagoDTO;
import com.mingeso.topeducation_ms2.entities.Pago;
import com.mingeso.topeducation_ms2.services.PagoService;
import com.mingeso.topeducation_ms2.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<PagosResponse> obtenerPagos(){
        List<Pago> pagos = pagoService.obtenerPagos();
        return new ResponseEntity<PagosResponse>(
                new PagosResponse(
                        HttpStatus.OK.value(),
                        "Pago realizado con éxito.",
                        Mapper.pagosToPagosDTO(pagos)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<PagoResponse> registrarPago(@RequestBody RegistrarPagoDTO registrarPagoDTO){
        Pago pago = pagoService.registrarPago(registrarPagoDTO);
        return new ResponseEntity<PagoResponse>(
                new PagoResponse(
                        HttpStatus.CREATED.value(),
                        "Pago realizado con éxito.",
                        Mapper.pagoToPagoDTO(pago)
                ),
                HttpStatus.CREATED
        );
    }
}
