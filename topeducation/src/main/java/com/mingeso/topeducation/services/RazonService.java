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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

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

    @Transactional
    public void generarRazonesEstudiante(String rut){
        try{
            Estudiante estudiante = estudianteRepository.findByRut(rut);


            ArrayList<Razon> razones = new ArrayList<>();
            for(int i = 0; i < 15; i++){

            }


        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
