package com.mingeso.topeducation_ms2.services;

import com.mingeso.topeducation_ms2.dtos.EstudianteResponseDTO;
import com.mingeso.topeducation_ms2.dtos.EstudiantesResponseDTO;
import com.mingeso.topeducation_ms2.dtos.EstudianteDTO;
import com.mingeso.topeducation_ms2.entities.Razon;
import com.mingeso.topeducation_ms2.exceptions.ApiErrorException;
import com.mingeso.topeducation_ms2.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation_ms2.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms2.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation_ms2.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class RazonService {
    RazonRepository razonRepository;
    TipoRazonRepository tipoRazonRepository;
    EstadoRazonRepository estadoRazonRepository;
    TotalRazonRepository totalRazonRepository;
    DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository;
    DescuentoTipoColegioRepository descuentoTipoColegioRepository;
    DescuentoAnioEgresoRepository descuentoAnioEgresoRepository;
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    WebClient webClient;

//    ExamenRepository examenRepository;
//    InteresMesesAtrasoRepository interesMesesAtrasoRepository;
//    MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;

    @Autowired
    RazonService(RazonRepository razonRepository,
                 TipoRazonRepository tipoRazonRepository,
                 EstadoRazonRepository estadoRazonRepository,
                 TotalRazonRepository totalRazonRepository,
                 DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository,
                 DescuentoTipoColegioRepository descuentoTipoColegioRepository,
                 DescuentoAnioEgresoRepository descuentoAnioEgresoRepository,
                 DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
                 WebClient.Builder webClientBuilder
//                 MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository
    ){
        this.razonRepository = razonRepository;
        this.tipoRazonRepository = tipoRazonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
        this.totalRazonRepository = totalRazonRepository;
        this.descuentoTipoPagoArancelRepository = descuentoTipoPagoArancelRepository;
        this.descuentoTipoColegioRepository = descuentoTipoColegioRepository;
        this.descuentoAnioEgresoRepository = descuentoAnioEgresoRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8001").build();
//        this.maxCuotasTipoColegioRepository = maxCuotasTipoColegioRepository;
    }

    public EstudianteDTO obtenerEstudiantePorRut(String rut){
        EstudianteResponseDTO response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/estudiantes")
                        .queryParam("rut", rut)// Ruta adicional
                        .build())
                .retrieve()
                .bodyToMono(EstudianteResponseDTO.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RegistroNoExisteException("Estudiante no encontrado");
                    } else {
                        throw new ApiErrorException("Error al obtener el estudiante.");
                    }
                })
                .block();
        if(response == null) throw new ApiErrorException("Error al obtener el estudiante.");
        return response.getData().get("estudiante");
    }

    @Transactional
    public List<Razon> generarCuotas(String rut, Integer numCuotas){
        EstudianteDTO estudiante = obtenerEstudiantePorRut(rut);
        LocalDate fechaInicioProcesoAnual = LocalDate.of(LocalDate.now().getYear(), Month.MARCH, 1);
        LocalDate fechaFinProcesoAnual = LocalDate.of(LocalDate.now().getYear() + 1, Month.JANUARY, 10);
        Long razonesAnioExistentes = razonRepository.existenRazonesProcesoByIdEstudiante(
                estudiante.getId(),
                fechaInicioProcesoAnual,
                fechaFinProcesoAnual);
        if(razonesAnioExistentes > 0) throw new FechaNoPermitidaException("Ya se generaron las cuotas correspondientes a este proceso.");
        if(numCuotas < 0) throw new ValorFueraDeRangoException("El número de cuotas es menor a 0.");
//        if(numCuotas > maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.get().getTipoColegio().getId())) throw new ValorFueraDeRangoException("El número de cuotas excede el máximo.");

//        Integer totalMatricula = totalRazonRepository.findTotalByTipoRazon("MATRICULA");
//        Integer totalArancel = totalRazonRepository.findTotalByTipoRazon("ARANCEL");
//
//        //Descuento por pagar en cuotas es 0, pero podría cambiar.
//        Integer porcentajeDescuento = descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.get().getTipoPagoArancel().getId());
//
//        //Si paga en cuotas
//        if(estudiante.get().getTipoPagoArancel().getId() == 0){
//            porcentajeDescuento += descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.get().getTipoColegio().getId());
//            porcentajeDescuento += descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.get().getAnioEgreso());
//        }
//
//        //Aplicar porcentaje descuentos
//        totalArancel = totalArancel - ((totalArancel * porcentajeDescuento) / 100);
//
//        // Obtén la fecha actual
//        LocalDate fechaActual = LocalDate.now();
//
//        LocalDate fechaInicioClases = LocalDate.of(fechaActual.getYear(), Month.MARCH, 1);
//        LocalDate fechaInicioMatricula = fechaInicioClases.minusDays(5);
//
//
//        TipoRazon tipoMatricula = tipoRazonRepository.findById(0).get();
//        EstadoRazon estadoPendiente = estadoRazonRepository.findById(1).get();
//        EstadoRazon estadoAtrasada = estadoRazonRepository.findById(2).get();
//
//        // Matricula
//        EstadoRazon estadoMatricula = LocalDate.now().isAfter(fechaInicioMatricula) ? estadoAtrasada : estadoPendiente;
//        Razon matricula = new Razon(0, totalMatricula, fechaInicioMatricula, fechaInicioClases, tipoMatricula, estadoMatricula, estudiante.get());
//        Razon resultadoMatricula = razonRepository.save(matricula);
//
//        List<Razon> razones = new ArrayList<>();
//        razones.add(resultadoMatricula);
//
//        //Generar cuotas arancel
//        LocalDate fechaInicioArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 5);
//        LocalDate fechaFinArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 10);
//
//        Integer cuota = totalArancel / numCuotas;
//        TipoRazon tipoArancel = tipoRazonRepository.findById(1).get();
//
//        for(int i = 0; i < numCuotas; i++){
//            Integer numero = i + 1;
//            EstadoRazon estadoArancel = LocalDate.now().isAfter(fechaFinArancel) ? estadoAtrasada : estadoPendiente;
//            Razon arancel = new Razon(numero, cuota, fechaInicioArancel, fechaFinArancel, tipoArancel, estadoArancel, estudiante.get());
//            Razon resultadoArancel = razonRepository.save(arancel);
//            razones.add(resultadoArancel);
//            fechaInicioArancel = fechaInicioArancel.plusMonths(1);
//            fechaFinArancel = fechaFinArancel.plusMonths(1);
//        }
//
//        estudiante.get().setCuotasPactadas(numCuotas);
//        estudianteRepository.save(estudiante.get());

//        return razones;
        return new ArrayList<>();
    }
}
