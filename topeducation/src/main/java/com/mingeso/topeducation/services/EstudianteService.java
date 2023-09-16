package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.InteresMesesAtraso;
import com.mingeso.topeducation.entities.TipoColegio;
import com.mingeso.topeducation.entities.TipoPagoArancel;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.InteresMesesAtrasoRepository;
import com.mingeso.topeducation.repositories.TipoColegioRepository;
import com.mingeso.topeducation.repositories.TipoPagoArancelRepository;
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
}
