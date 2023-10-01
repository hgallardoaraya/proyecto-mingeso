package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Pago;
import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.PagoRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final EstudianteRepository estudianteRepository;
    private final RazonRepository razonRepository;
    @Autowired
    public PagoService(PagoRepository pagoRepository, EstudianteRepository estudianteRepository, RazonRepository razonRepository){
        this.pagoRepository = pagoRepository;
        this.estudianteRepository = estudianteRepository;
        this.razonRepository = razonRepository;
    }

    //Existe un pago que contiene todas las razones que se desean pagar.
    @Transactional
    public void registrarPago(String rut, Integer[] idsRazones) {
        try{
            Pago pago = new Pago();
            Estudiante estudiante = estudianteRepository.findByRut(rut);
            pago.setEstudiante(estudiante);
            if(estudiante == null) throw new RuntimeException("Error, el estudiante no existe.");

            List<Razon> razones = new ArrayList<>();
            Integer total = 0;
            for(Integer idRazon : idsRazones){
                Razon razon = razonRepository.findById(idRazon).get();
                razones.add(razon);
                total += razon.getMonto();
            }
            if(razones == null) throw new RuntimeException("Error no se indicaron o no existen razones.");
            pago.setRazones(razones);
            pago.setTotal(total);
            pago.setFecha(LocalDate.now());
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
