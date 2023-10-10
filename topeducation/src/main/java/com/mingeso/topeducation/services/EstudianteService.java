package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation.repositories.*;
import com.mingeso.topeducation.requests.IngresarEstudianteRequest;
import com.mingeso.topeducation.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
     
    private final TipoColegioRepository tipoColegioRepository;
    private final TipoPagoArancelRepository tipoPagoArancelRepository;
    private final InteresMesesAtrasoRepository interesMesesAtrasoRepository;
    private final MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository,
                             TipoColegioRepository tipoColegioRepository,
                             TipoPagoArancelRepository tipoPagoArancelRepository,
                             InteresMesesAtrasoRepository interesMesesAtrasoRepository,
                             MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository){
        this.estudianteRepository = estudianteRepository;
        this.tipoColegioRepository = tipoColegioRepository;
        this.tipoPagoArancelRepository = tipoPagoArancelRepository;
        this.interesMesesAtrasoRepository = interesMesesAtrasoRepository;
        this.maxCuotasTipoColegioRepository = maxCuotasTipoColegioRepository;
    }

    public Estudiante ingresarEstudiante(IngresarEstudianteRequest request){
        Estudiante estudiante = request.getEstudiante();
        Integer idTipoColegio = request.getIdTipoColegio();
        Integer idTipoPagoArancel = request.getIdTipoPagoArancel();
        Integer idInteresMesesAtraso = request.getIdInteresMesesAtraso();

        Optional<TipoColegio> tipoColegio = tipoColegioRepository.findById(idTipoColegio);
        if(tipoColegio.isEmpty()) throw new RegistroNoExisteException("El colegio " + idTipoColegio + " no existe.");

        Optional<TipoPagoArancel> tipoPagoArancel = tipoPagoArancelRepository.findById(idTipoPagoArancel);
        if(tipoPagoArancel.isEmpty()) throw new RegistroNoExisteException("El tipo de pago arancel " + idTipoPagoArancel + " no existe.");

        Optional<InteresMesesAtraso> interesMesesAtraso = interesMesesAtrasoRepository.findById(idInteresMesesAtraso);
        if(interesMesesAtraso.isEmpty()) throw new RegistroNoExisteException("El interes por meses de atraso " + idInteresMesesAtraso + " no existe.");

        estudiante.setTipoColegio(tipoColegio.get());
        estudiante.setTipoPagoArancel(tipoPagoArancel.get());
        estudiante.setInteresMesesAtraso(interesMesesAtraso.get());

        return estudianteRepository.save(estudiante);
    }

    public Integer getMaxCuotasByRut(String rut){
        Optional<Estudiante> estudiante = estudianteRepository.findByRut(rut);
        if(estudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante con rut " + rut + " no existe.");
        return maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.get().getTipoColegio().getId());
    }
}
