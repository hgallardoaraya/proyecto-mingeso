package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import com.mingeso.topeducation.requests.SaveEstudianteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    TipoColegioRepository tipoColegioRepository;
    @Autowired
    TipoPagoArancelRepository tipoPagoArancelRepository;
    @Autowired
    InteresMesesAtrasoRepository interesMesesAtrasoRepository;
    @Autowired
    MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;

    public void saveEstudiante(SaveEstudianteRequest request){
        try{
            Estudiante estudiante = request.getEstudiante();
            Integer idTipoColegio = request.getIdTipoColegio();
            TipoColegio tipoColegio = tipoColegioRepository.findById(idTipoColegio).get();
            Integer idTipoPagoArancel = request.getIdTipoPagoArancel();
            TipoPagoArancel tipoPagoArancel = tipoPagoArancelRepository.findById(idTipoPagoArancel).get();
            Integer idInteresMesesAtraso = request.getIdInteresMesesAtraso();
            InteresMesesAtraso interesMesesAtraso = interesMesesAtrasoRepository.findById(idInteresMesesAtraso).get();

            estudiante.setTipoColegio(tipoColegio);
            estudiante.setTipoPagoArancel(tipoPagoArancel);
            estudiante.setInteresMesesAtraso(interesMesesAtraso);

            estudianteRepository.save(estudiante);
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public Integer getMaxCuotasByRut(String rut){
        try{
            Estudiante estudiante = estudianteRepository.findByRut(rut);
            return maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.getTipoColegio().getId());
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
