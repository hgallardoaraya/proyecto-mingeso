package com.mingeso.topeducation_ms2.services;

import com.mingeso.topeducation_ms2.dtos.estudiantes.EstudianteDTO;
import com.mingeso.topeducation_ms2.dtos.pagos.RegistrarPagoDTO;
import com.mingeso.topeducation_ms2.entities.EstadoRazon;
import com.mingeso.topeducation_ms2.entities.Pago;
import com.mingeso.topeducation_ms2.entities.Razon;
import com.mingeso.topeducation_ms2.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation_ms2.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms2.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation_ms2.repositories.EstadoRazonRepository;
import com.mingeso.topeducation_ms2.repositories.PagoRepository;
import com.mingeso.topeducation_ms2.repositories.RazonRepository;
import com.mingeso.topeducation_ms2.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final EstadoRazonRepository estadoRazonRepository;
    private final RazonRepository razonRepository;
    private final EstudianteService estudianteService;

    @Autowired
    public PagoService(PagoRepository pagoRepository,
                       EstadoRazonRepository estadoRazonRepository,
                       RazonRepository razonRepository,
                       EstudianteService estudianteService){
        this.pagoRepository = pagoRepository;
        this.estadoRazonRepository = estadoRazonRepository;
        this.razonRepository = razonRepository;
        this.estudianteService = estudianteService;
    }

    public List<Pago> obtenerPagos(){
        return pagoRepository.findAll();
    }

    @Transactional
    public Pago registrarPago(RegistrarPagoDTO registrarPagoDTO) {
        LocalDate fechaActual = LocalDate.now();

//        if(fechaActual.isBefore(Util.obtenerFechaInicioProceso(fechaActual)))
//            throw new FechaNoPermitidaException("No está permitido pagar fuera del rango del 5 y el 10 de cada mes.");

        Pago pago = new Pago();

        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorRut(registrarPagoDTO.getRut());
        pago.setIdEstudiante(estudiante.getId());

        List<Razon> razones = new ArrayList<>();
        Integer total = 0;
        for(Integer idRazon : registrarPagoDTO.getIdsRazones()){
            Optional<Razon> razon = razonRepository.findById(idRazon);
            if(razon.isEmpty()) throw new RegistroNoExisteException("No existe la razon con id " + idRazon + ".");
            if(razon.get().getMonto() <= 0) throw new ValorFueraDeRangoException("El monto de la cuota no debe ser menor" +
                    " o igual a 0.");
            //estado "PAGADA"
            Optional<EstadoRazon> estadoPagada = estadoRazonRepository.findById(1);
            if(estadoPagada.isEmpty()) throw new RegistroNoExisteException("Error al obtener el estado de id 1.");
            razon.get().setEstado(estadoPagada.get());
            razones.add(razon.get());
            total += razon.get().getMonto();
        }

        List<Razon> razonesResultantes = razonRepository.saveAll(razones);

        if(razones.isEmpty() || razonesResultantes.isEmpty()) throw new RegistroNoExisteException("No se indicaron o no existen razones.");

        pago.setRazones(razones);
        pago.setTotal(total);
        pago.setFecha(LocalDate.now());

        return pagoRepository.save(pago);
    }
}
