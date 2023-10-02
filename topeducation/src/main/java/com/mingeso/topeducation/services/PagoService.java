package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.EstadoRazon;
import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.Pago;
import com.mingeso.topeducation.entities.Razon;
import com.mingeso.topeducation.repositories.EstadoRazonRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.PagoRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import com.mingeso.topeducation.requests.RegistrarPagoRequest;
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
    private final EstadoRazonRepository estadoRazonRepository;
    @Autowired
    public PagoService(PagoRepository pagoRepository, EstudianteRepository estudianteRepository, RazonRepository razonRepository, EstadoRazonRepository estadoRazonRepository){
        this.pagoRepository = pagoRepository;
        this.estudianteRepository = estudianteRepository;
        this.razonRepository = razonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
    }

    //Existe un pago que contiene todas las razones que se desean pagar.
    @Transactional
    public void registrarPago(RegistrarPagoRequest request) {
        try{
            Pago pago = new Pago();
            Estudiante estudiante = estudianteRepository.findByRut(request.getRut());
            pago.setEstudiante(estudiante);
            if(estudiante == null) throw new RuntimeException("Error, el estudiante no existe.");

            List<Razon> razones = new ArrayList<>();
            Integer total = 0;
            for(Integer idRazon : request.getIdsRazones()){
                Razon razon = razonRepository.findById(idRazon).get();
                //estado "PAGADA"
                EstadoRazon estadoRazon = estadoRazonRepository.findById(0).get();
                razon.setEstado(estadoRazon);
                razones.add(razon);
                razonRepository.save(razon);
                total += razon.getMonto();
            }
            if(razones == null) throw new RuntimeException("Error no se indicaron o no existen razones.");
            pago.setRazones(razones);
            pago.setTotal(total);
            pago.setFecha(LocalDate.now());
            pagoRepository.save(pago);
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public ArrayList<Razon> obtenerRazonesPendientesDePago(String rut) {
        return razonRepository.findAllPendientesByRut(rut);
    }
}
