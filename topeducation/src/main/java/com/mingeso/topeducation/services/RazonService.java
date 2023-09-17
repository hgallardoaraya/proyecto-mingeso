package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.EstadoRazon;
import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.entities.TipoRazon;
import com.mingeso.topeducation.repositories.EstadoRazonRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import com.mingeso.topeducation.repositories.TipoRazonRepository;
import com.mingeso.topeducation.requests.GenerarRazonesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void generarRazones(GenerarRazonesRequest request){
        try{
            Razon razon = request.getRazon();

            Integer idTipoRazon = request.getIdTipoRazon();
            TipoRazon tipoRazon = tipoRazonRepository.findById(idTipoRazon).get();

            Integer idEstadoRazon = request.getIdEstadoRazon();
            EstadoRazon estadoRazon = estadoRazonRepository.findById(idEstadoRazon).get();

            String rutEstudiante = request.getRutEstudiante();
            Estudiante estudiante = estudianteRepository.findByRut(rutEstudiante);

            razon.setTipo(tipoRazon);
            razon.setEstado(estadoRazon);
            razon.setEstudiante(estudiante);

            razonRepository.save(razon);
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
