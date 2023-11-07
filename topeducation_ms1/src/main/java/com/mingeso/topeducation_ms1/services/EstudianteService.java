package com.mingeso.topeducation_ms1.services;

import com.mingeso.topeducation_ms1.dtos.IngresarEstudianteDTO;
import com.mingeso.topeducation_ms1.entities.Estudiante;
import com.mingeso.topeducation_ms1.entities.TipoColegio;
import com.mingeso.topeducation_ms1.entities.TipoPagoArancel;
import com.mingeso.topeducation_ms1.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms1.repositories.EstudianteRepository;
import com.mingeso.topeducation_ms1.repositories.TipoColegioRepository;
import com.mingeso.topeducation_ms1.repositories.TipoPagoArancelRepository;
import com.mingeso.topeducation_ms1.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private TipoColegioRepository tipoColegioRepository;
    @Autowired
    private TipoPagoArancelRepository tipoPagoArancelRepository;

    public Estudiante ingresarEstudiante(IngresarEstudianteDTO ingresarEstudianteDTO){
        Integer idTipoColegio = ingresarEstudianteDTO.getIdTipoColegio();
        Integer idTipoPagoArancel = ingresarEstudianteDTO.getIdTipoPagoArancel();

        Optional<TipoColegio> tipoColegio = tipoColegioRepository.findById(idTipoColegio);
        if(tipoColegio.isEmpty()) throw new RegistroNoExisteException("El colegio " + idTipoColegio + " no existe.");

        Optional<TipoPagoArancel> tipoPagoArancel = tipoPagoArancelRepository.findById(idTipoPagoArancel);
        if(tipoPagoArancel.isEmpty()) throw new RegistroNoExisteException("El tipo de pago arancel " + idTipoPagoArancel + " no existe.");

        Estudiante estudiante = Mapper.ingresarEstudianteDTOToEstudianteEntity(ingresarEstudianteDTO);
        estudiante.setCuotasPactadas(0);
        estudiante.setTipoColegio(tipoColegio.get());
        estudiante.setTipoPagoArancel(tipoPagoArancel.get());

        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return estudiantes;
    }

    public Estudiante obtenerEstudiantePorRut(String rut) {
        Optional<Estudiante> estudiante = estudianteRepository.findByRut(rut);
        if(estudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante con rut " + rut + " no existe.");
        return estudiante.get();
    }

    public Estudiante actualizarCuotasPactadas(Integer idEstudiante, Integer cuotasPactadas) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(idEstudiante);
        if(estudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante consultado no existe.");
        estudiante.get().setCuotasPactadas(cuotasPactadas);
        return estudianteRepository.save(estudiante.get());
    }
}


