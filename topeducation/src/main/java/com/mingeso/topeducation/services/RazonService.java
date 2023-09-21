package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class RazonService {
    @Autowired
    RazonRepository razonRepository;
    @Autowired
    TipoRazonRepository tipoRazonRepository;
    @Autowired
    EstadoRazonRepository estadoRazonRepository;
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    TotalRazonRepository totalRazonRepository;
    @Autowired
    DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository;
    @Autowired
    DescuentoTipoColegioRepository descuentoTipoColegioRepository;
    @Autowired
    DescuentoAnioEgresoRepository descuentoAnioEgresoRepository;

    @Transactional
    public void generarCuotas(String rut, Integer numCuotas){
        try{
            Estudiante estudiante = estudianteRepository.findByRut(rut);

            Integer totalMatricula = totalRazonRepository.findTotalByTipoRazon("MATRICULA");
            Integer totalArancel = totalRazonRepository.findTotalByTipoRazon("ARANCEL");

            //Descuento por pagar en cuotas es 0, pero podría cambiar.
            Integer porcentajeDescuento = descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.getTipoPagoArancel().getId());

            //Si paga en cuotas o al contado.
            if(estudiante.getTipoPagoArancel().getId() == 0){
                porcentajeDescuento += descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.getTipoColegio().getId());
                porcentajeDescuento += descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.getAnioEgreso());
            }

            //Aplicar porcentaje descuentos
            totalArancel = totalArancel - ((totalArancel * porcentajeDescuento) / 100);

            //Generar cuotas
            Integer cuota = totalArancel / numCuotas;
            EstadoRazon estadoRazon = estadoRazonRepository.findById(1).get();
            TipoRazon tipoRazon = tipoRazonRepository.findById(1).get();
            // Obtén la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Obtén el mes actual
            Month mesActual = fechaActual.getMonth();

            // Calcula el mes del próximo 5
            Month mesProximo5;
            if (mesActual == Month.DECEMBER) {
                mesProximo5 = Month.JANUARY;
            } else {
                mesProximo5 = mesActual.plus(1);
            }
            // Crea la fecha del próximo 5 del próximo mes
            LocalDate fechaInicio = LocalDate.of(fechaActual.getYear(), mesProximo5, 5);
            LocalDate fechaFin = LocalDate.of(fechaActual.getYear(), mesProximo5, 10);

            for(Integer i = 0; i < numCuotas; i++){
                Integer numero = i + 1;
                Razon razon = new Razon(numero, cuota, fechaInicio, fechaFin, tipoRazon, estadoRazon, estudiante);
                razonRepository.save(razon);
            }

        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
