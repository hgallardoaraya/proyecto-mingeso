package com.mingeso.topeducation_ms2.services;

import com.mingeso.topeducation_ms2.dtos.EstudianteDTO;
import com.mingeso.topeducation_ms2.entities.EstadoRazon;
import com.mingeso.topeducation_ms2.entities.Razon;
import com.mingeso.topeducation_ms2.entities.TipoRazon;
import com.mingeso.topeducation_ms2.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation_ms2.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation_ms2.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    EstudianteService estudianteService;
    MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;
//    ExamenRepository examenRepository;
//    InteresMesesAtrasoRepository interesMesesAtrasoRepository;

    @Autowired
    RazonService(RazonRepository razonRepository,
                 TipoRazonRepository tipoRazonRepository,
                 EstadoRazonRepository estadoRazonRepository,
                 TotalRazonRepository totalRazonRepository,
                 DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository,
                 DescuentoTipoColegioRepository descuentoTipoColegioRepository,
                 DescuentoAnioEgresoRepository descuentoAnioEgresoRepository,
                 DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
                 EstudianteService estudianteService,
                 MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository
    ){
        this.razonRepository = razonRepository;
        this.tipoRazonRepository = tipoRazonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
        this.totalRazonRepository = totalRazonRepository;
        this.descuentoTipoPagoArancelRepository = descuentoTipoPagoArancelRepository;
        this.descuentoTipoColegioRepository = descuentoTipoColegioRepository;
        this.descuentoAnioEgresoRepository = descuentoAnioEgresoRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.maxCuotasTipoColegioRepository = maxCuotasTipoColegioRepository;
        this.estudianteService = estudianteService;
    }

    @Transactional
    public List<Razon> generarCuotas(String rut, Integer numCuotas){
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorRut(rut);
        verificarRazonesProcesoByIdEstudiante(estudiante.getId());
        if(numCuotas < 0)
            throw new ValorFueraDeRangoException("El número de cuotas es menor a 0.");
        if(numCuotas > maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.getTipoColegio().getId()))
            throw new ValorFueraDeRangoException("El número de cuotas excede el máximo.");

        Integer totalMatricula = totalRazonRepository.findTotalByTipoRazon("Matricula");
        Integer totalArancel = calcularTotalArancelConDescuentos(estudiante);

        // Obtén la fecha actual
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioClases = LocalDate.of(fechaActual.getYear(), Month.MARCH, 1);
        LocalDate fechaInicioMatricula = fechaInicioClases.minusDays(5);


        TipoRazon tipoMatricula = tipoRazonRepository.findById(1).get();
        EstadoRazon estadoPendiente = estadoRazonRepository.findById(2).get();
        EstadoRazon estadoAtrasada = estadoRazonRepository.findById(3).get();

        // Matricula
        EstadoRazon estadoMatricula = LocalDate.now().isAfter(fechaInicioMatricula) ? estadoAtrasada : estadoPendiente;
        Razon matricula = new Razon(0, totalMatricula, fechaInicioMatricula, fechaInicioClases, tipoMatricula, estadoMatricula, estudiante.getId());

        List<Razon> razones = new ArrayList<>();
        razones.add(matricula);

        //Generar cuotas arancel
        LocalDate fechaInicioArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 5);
        LocalDate fechaFinArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 10);

        Integer cuota = totalArancel / numCuotas;
        TipoRazon tipoArancel = tipoRazonRepository.findById(2).get();

        for(int i = 0; i < numCuotas; i++){
            Integer numero = i + 1;
            EstadoRazon estadoArancel = LocalDate.now().isAfter(fechaFinArancel) ? estadoAtrasada : estadoPendiente;
            Razon arancel = new Razon(numero, cuota, fechaInicioArancel, fechaFinArancel, tipoArancel, estadoArancel, estudiante.getId());
            razones.add(arancel);
            fechaInicioArancel = fechaInicioArancel.plusMonths(1);
            fechaFinArancel = fechaFinArancel.plusMonths(1);
        }

        estudianteService.actualizarCuotasPactadas(estudiante.getId(), numCuotas);

        return razonRepository.saveAll(razones);
    }

    public void verificarRazonesProcesoByIdEstudiante(Integer idEstudiante){
        LocalDate fechaInicioProcesoAnual = LocalDate.of(LocalDate.now().getYear(), Month.MARCH, 1);
        LocalDate fechaFinProcesoAnual = LocalDate.of(LocalDate.now().getYear() + 1, Month.JANUARY, 10);
        Long razonesAnioExistentes = razonRepository.findCantidadRazonesProcesoByIdEstudiante(
                idEstudiante,
                fechaInicioProcesoAnual,
                fechaFinProcesoAnual);
        if(razonesAnioExistentes > 0) throw new FechaNoPermitidaException("Ya se generaron las cuotas correspondientes a este proceso.");
    }

    public Integer calcularTotalArancelConDescuentos(EstudianteDTO estudiante){
        Integer totalArancel = totalRazonRepository.findTotalByTipoRazon("Arancel");

        //Descuento por pagar en cuotas es 0, pero podría cambiar.
        Integer porcentajeDescuento = descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.getTipoPagoArancel().getId());

        //Si paga en cuotas
        if(estudiante.getTipoPagoArancel().getTipo().equals("Cuotas")){
            porcentajeDescuento += descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.getTipoColegio().getId());
            porcentajeDescuento += descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.getAnioEgreso());
        }

        //Aplicar porcentaje descuentos
        return totalArancel - ((totalArancel * porcentajeDescuento) / 100);
    }
}
