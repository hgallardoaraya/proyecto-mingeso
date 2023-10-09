package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.exceptions.EstudianteNoExisteException;
import com.mingeso.topeducation.exceptions.InteresMesesAtrasoNoExisteException;
import com.mingeso.topeducation.exceptions.TipoColegioNoExisteException;
import com.mingeso.topeducation.exceptions.TipoPagoArancelNoExisteException;
import com.mingeso.topeducation.repositories.*;
import com.mingeso.topeducation.requests.IngresarEstudianteRequest;
import com.mingeso.topeducation.responses.Response;
import jakarta.persistence.EntityNotFoundException;
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

    public ResponseEntity<Response> ingresarEstudiante(IngresarEstudianteRequest request){
        Estudiante estudiante = request.getEstudiante();
        Integer idTipoColegio = request.getIdTipoColegio();
        Integer idTipoPagoArancel = request.getIdTipoPagoArancel();
        Integer idInteresMesesAtraso = request.getIdInteresMesesAtraso();

        Optional<TipoColegio> tipoColegio = tipoColegioRepository.findById(idTipoColegio);
        if(tipoColegio.isEmpty()) throw new TipoColegioNoExisteException("El colegio " + idTipoColegio + " no existe.");

        Optional<TipoPagoArancel> tipoPagoArancel = tipoPagoArancelRepository.findById(idTipoPagoArancel);
        if(tipoPagoArancel.isEmpty()) throw new TipoPagoArancelNoExisteException("El tipo de pago arancel " + idTipoPagoArancel + " no existe.");

        Optional<InteresMesesAtraso> interesMesesAtraso = interesMesesAtrasoRepository.findById(idInteresMesesAtraso);
        if(interesMesesAtraso.isEmpty()) throw new InteresMesesAtrasoNoExisteException("El interes por meses de atraso " + idInteresMesesAtraso + " no existe.");

        estudiante.setTipoColegio(tipoColegio.get());
        estudiante.setTipoPagoArancel(tipoPagoArancel.get());
        estudiante.setInteresMesesAtraso(interesMesesAtraso.get());

        return new ResponseEntity<>(new Response(
                    HttpStatus.CREATED.value(),
                    "Estudiante ingresado correctamente.",
                    "/estudiantes/exito",
                    estudianteRepository.save(estudiante)
                ),
                HttpStatus.CREATED);
    }

    public ResponseEntity<Response> getMaxCuotasByRut(String rut){
        Optional<Estudiante> estudiante = estudianteRepository.findByRut(rut);
        if(estudiante.isEmpty()) throw new EstudianteNoExisteException("El estudiante con rut " + rut + " no existe.");

        Integer maxCuotas = maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.get().getTipoColegio().getId());

        Map<String, Integer> data = new HashMap<>();
        data.put("maxCuotas", maxCuotas);
        return new ResponseEntity<Response>(new Response(
                    HttpStatus.FOUND.value(),
                    "Número máximo de cuotas encontrado con éxito.",
                    data
                ),
                HttpStatus.FOUND);
    }
}
