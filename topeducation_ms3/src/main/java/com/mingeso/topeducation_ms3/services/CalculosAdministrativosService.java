package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.razones.RazonDTO;
import com.mingeso.topeducation_ms3.entities.DescuentoPuntajePrueba;
import com.mingeso.topeducation_ms3.entities.Examen;
import com.mingeso.topeducation_ms3.exceptions.FechaNoPermitidaException;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms3.repositories.DescuentoPuntajePruebaRepository;
import com.mingeso.topeducation_ms3.repositories.ExamenRepository;
import com.mingeso.topeducation_ms3.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculosAdministrativosService {
    ExamenRepository examenRepository;
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    RazonService razonService;

    @Autowired
    public CalculosAdministrativosService(
            ExamenRepository examenRepository,
            DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
            RazonService razonService
            ){
        this.examenRepository = examenRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.razonService = razonService;
    }

    @Transactional
    public void calcularPlanilla(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioProceso = Util.obtenerFechaInicioProceso(fechaActual);
        LocalDate fechaExamenesProceso = Util.obtenerFechaExamenesProceso(fechaInicioProceso);
//        if(fechaActual.isBefore(fechaExamenesProceso) || fechaActual.isAfter(fechaInicioProceso.minusDays(1))){
//            throw new FechaNoPermitidaException("El cálculo de planilla del proceso correspondiente debe ser realizado"
//                    + " entre el último viernes del més (cuando se rindieron los últimos exámenes) " + fechaExamenesProceso
//                    + " y el inicio de pago de arancel del proceso "
//                    + fechaInicioProceso + ".");
//        }

        aplicarDescuentosPorPuntajes();

//        return aplicarInteresesPorMesesAtraso(fechaActual);
    }

    @Transactional
    public void aplicarDescuentosPorPuntajes(){
        LocalDate fechaActual = LocalDate.now();
        List<Examen> examenes = examenRepository.findAllSinRevision();

        if(examenes == null || examenes.isEmpty()){
            throw new RegistroNoExisteException("No hay exámenes para ser revisados.");
        }

        //Recibe exactamente los exámenes rendidos el último viernes del mes antes del próximo proceso de pago.
        //Es importante por lo mismo no equivocarse en las fechas de los exámenes del Excel.
        List<Examen> examenesProcesoActual = examenes
                .stream()
                .filter(examen -> examen.getFecha().isEqual(Util.obtenerFechaExamenesProceso(Util.obtenerFechaInicioProceso(fechaActual))))
                .toList();

        Map<Integer, List<Integer>> idsPuntajes = obtenerPuntajesPorId(examenesProcesoActual);

        actualizarRevisionExamenes(examenesProcesoActual);

        HashMap<Integer, List<RazonDTO>> cuotasResultantes = new HashMap<>();

        List<RazonDTO> cuotasPendientesYAtrasadas = razonService.obtenerCuotasPendientesYAtrasadasPorIdsEstudiantes(idsPuntajes.keySet().toArray(new Integer[idsPuntajes.keySet().size()]));
        Map<Integer, List<RazonDTO>> idsCuotas = obtenerRazonesPorId(cuotasPendientesYAtrasadas);

        for(Integer idEstudiante : idsPuntajes.keySet()){
            //Calcular total
            Integer total = idsPuntajes.get(idEstudiante).stream().reduce(0, Integer::sum);
            //Calcular promedio
            //No es necesario verificar los limites del promedio ya que los datos fueron limpiados al importar los
            //exámenes.
            Integer promedio = total/idsPuntajes.get(idEstudiante).size();
            Integer porcentajeDescuento = descuentoPuntajePruebaRepository.findDescuentoByPuntajePromedio(promedio);

            List<RazonDTO> cuotasArancel = idsCuotas.get(idEstudiante);
            if(cuotasArancel != null && cuotasArancel.size() > 0){
                for(RazonDTO cuota : cuotasArancel){
                    //aplicar descuento
                    cuota.setMonto(cuota.getMonto() - ((cuota.getMonto() * porcentajeDescuento) / 100));

                    cuotasResultantes
                            .computeIfAbsent(idEstudiante, k -> new ArrayList<>())
                            .add(cuota);
                }
            }
        }

        List<RazonDTO> arregloCuotasResultantes = new ArrayList<>();

        for(List<RazonDTO> razones : cuotasResultantes.values()){
            arregloCuotasResultantes.addAll(razones);
        }

        razonService.actualizarCuotas(arregloCuotasResultantes);
    }
//
    public Map<Integer, List<Integer>> obtenerPuntajesPorId(List<Examen> examenes){
        Map<Integer, List<Integer>> idsPuntajes = new HashMap<>();

        for(Examen examen : examenes){
            Integer idEstudiante = examen.getIdEstudiante();
            idsPuntajes
                    .computeIfAbsent(idEstudiante, k -> new ArrayList<>())
                    .add(examen.getPuntaje());
        }
        return idsPuntajes;
    }

    public Map<Integer, List<RazonDTO>> obtenerRazonesPorId(List<RazonDTO> razones){
        Map<Integer, List<RazonDTO>> idsRazones = new HashMap<>();

        for(RazonDTO razon : razones){
            Integer idEstudiante = razon.getIdEstudiante();
            idsRazones
                    .computeIfAbsent(idEstudiante, k -> new ArrayList<>())
                    .add(razon);
        }
        return idsRazones;
    }

    public void actualizarRevisionExamenes(List<Examen> examenes){
        for(Examen examen : examenes){
            examen.setRevision(1);
        }
        examenRepository.saveAll(examenes);
    }

}
