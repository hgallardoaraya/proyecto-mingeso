package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class RazonService {
    RazonRepository razonRepository;
    TipoRazonRepository tipoRazonRepository;
    EstadoRazonRepository estadoRazonRepository;
    EstudianteRepository estudianteRepository;
    TotalRazonRepository totalRazonRepository;
    DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository;
    DescuentoTipoColegioRepository descuentoTipoColegioRepository;
    DescuentoAnioEgresoRepository descuentoAnioEgresoRepository;

    @Autowired
    RazonService(RazonRepository razonRepository,
                 TipoRazonRepository tipoRazonRepository,
                 EstadoRazonRepository estadoRazonRepository,
                 EstudianteRepository estudianteRepository,
                 TotalRazonRepository totalRazonRepository,
                 DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository,
                 DescuentoTipoColegioRepository descuentoTipoColegioRepository,
                 DescuentoAnioEgresoRepository descuentoAnioEgresoRepository
                 ){
        this.razonRepository = razonRepository;
        this.tipoRazonRepository = tipoRazonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
        this.estudianteRepository = estudianteRepository;
        this.totalRazonRepository = totalRazonRepository;
        this.descuentoTipoPagoArancelRepository = descuentoTipoPagoArancelRepository;
        this.descuentoTipoColegioRepository = descuentoTipoColegioRepository;
        this.descuentoAnioEgresoRepository = descuentoAnioEgresoRepository;
    }

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

            // Obtén la fecha actual
            LocalDate fechaActual = LocalDate.now();

            //cambiar esto a variable en bdd
            LocalDate fechaInicioClases = LocalDate.of(fechaActual.getYear(), Month.MARCH, 1);
            LocalDate fechaInicioMatricula = fechaInicioClases.minusDays(5);


            TipoRazon tipoMatricula = tipoRazonRepository.findById(0).get();
            EstadoRazon estadoPendiente = estadoRazonRepository.findById(1).get();

            // Matricula
            Razon matricula = new Razon(0, totalMatricula, fechaInicioMatricula, fechaInicioClases, tipoMatricula, estadoPendiente, estudiante);
            razonRepository.save(matricula);

            //Generar cuotas arancel
            LocalDate fechaInicioArancel = LocalDate.of(fechaActual.getYear(), Month.MARCH, 5);
            LocalDate fechaFinArancel = LocalDate.of(fechaActual.getYear(), Month.MARCH, 10);
            Integer cuota = totalArancel / numCuotas;
            TipoRazon tipoArancel = tipoRazonRepository.findById(1).get();

            for(Integer i = 0; i < numCuotas; i++){
                Integer numero = i + 1;
                Razon arancel = new Razon(numero, cuota, fechaInicioArancel, fechaFinArancel, tipoArancel, estadoPendiente, estudiante);
                razonRepository.save(arancel);
                fechaInicioArancel = fechaInicioArancel.plusMonths(1);
                fechaFinArancel = fechaFinArancel.plusMonths(1);
            }

        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public ArrayList<Razon> getRazones(String rut) {
        return razonRepository.findAllByRut(rut);
    }
}
