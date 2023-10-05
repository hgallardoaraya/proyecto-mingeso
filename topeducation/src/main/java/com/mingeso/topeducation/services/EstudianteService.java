package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import com.mingeso.topeducation.requests.SaveEstudianteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
