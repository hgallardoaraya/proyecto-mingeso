package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            totalArancel = totalArancel - ((totalArancel * porcentajeDescuento) / 100);

            for(Integer i = 0; i < numCuotas; i++){
                Razon cuota = new Razon();
                EstadoRazon estado = estadoRazonRepository.findById()
                cuota.setEstado();
                razonRepository.save();
            }

        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
