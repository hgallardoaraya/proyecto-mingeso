package com.mingeso.topeducationms1.services;

import com.mingeso.topeducationms1.dtos.IngresarEstudianteDTO;
import com.mingeso.topeducationms1.entities.Estudiante;
import com.mingeso.topeducationms1.entities.TipoColegio;
import com.mingeso.topeducationms1.entities.TipoPagoArancel;
import com.mingeso.topeducationms1.exceptions.RegistroNoExisteException;
import com.mingeso.topeducationms1.repositories.EstudianteRepository;
import com.mingeso.topeducationms1.repositories.TipoColegioRepository;
import com.mingeso.topeducationms1.repositories.TipoPagoArancelRepository;
import com.mingeso.topeducationms1.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final TipoColegioRepository tipoColegioRepository;
    private final TipoPagoArancelRepository tipoPagoArancelRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository,
                             TipoColegioRepository tipoColegioRepository,
                             TipoPagoArancelRepository tipoPagoArancelRepository){
        this.estudianteRepository = estudianteRepository;
        this.tipoColegioRepository = tipoColegioRepository;
        this.tipoPagoArancelRepository = tipoPagoArancelRepository;
    }

    public void ingresarEstudiante(IngresarEstudianteDTO ingresarEstudianteDTO){
        Integer idTipoColegio = ingresarEstudianteDTO.getIdTipoColegio();
        Integer idTipoPagoArancel = ingresarEstudianteDTO.getIdTipoPagoArancel();

        Optional<TipoColegio> tipoColegio = tipoColegioRepository.findById(idTipoColegio);
        TipoColegio tipoColegioEntity = tipoColegio.orElseThrow(() -> new RegistroNoExisteException("El colegio " + idTipoColegio + " no existe."));

        Optional<TipoPagoArancel> tipoPagoArancel = tipoPagoArancelRepository.findById(idTipoPagoArancel);
        TipoPagoArancel tipoPagoArancelEntity = tipoPagoArancel.orElseThrow(() -> new RegistroNoExisteException("El tipo de pago arancel " + idTipoPagoArancel + " no existe."));

        Estudiante estudiante = new Estudiante();
        estudiante.setRut(ingresarEstudianteDTO.getRut());
        estudiante.setNombre1(ingresarEstudianteDTO.getNombre1());
        estudiante.setNombre2(ingresarEstudianteDTO.getNombre2());
        estudiante.setApellido1(ingresarEstudianteDTO.getApellido1());
        estudiante.setApellido2(ingresarEstudianteDTO.getApellido2());
        estudiante.setFechaNacimiento(ingresarEstudianteDTO.getFechaNacimiento());
        estudiante.setAnioEgreso(ingresarEstudianteDTO.getAnioEgreso());
        estudiante.setNombreColegio(ingresarEstudianteDTO.getNombreColegio());
        estudiante.setCuotasPactadas(0);
        estudiante.setTipoColegio(tipoColegioEntity);
        estudiante.setTipoPagoArancel(tipoPagoArancelEntity);

        estudianteRepository.saveAndFlush(estudiante);
    }

    public List<Estudiante> obtenerEstudiantes() {
        return estudianteRepository.findAll();
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


