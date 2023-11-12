package com.mingeso.topeducation_ms3.services;

import com.mingeso.topeducation_ms3.dtos.estudiante.EstudianteDTO;
import com.mingeso.topeducation_ms3.dtos.pago.PagoDTO;
import com.mingeso.topeducation_ms3.dtos.pago.PagosResponse;
import com.mingeso.topeducation_ms3.dtos.razon.RazonDTO;
import com.mingeso.topeducation_ms3.dtos.reporte.EntradaReporteResumen;
import com.mingeso.topeducation_ms3.entities.Examen;
import com.mingeso.topeducation_ms3.entities.InteresMesesAtraso;
import com.mingeso.topeducation_ms3.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation_ms3.repositories.DescuentoPuntajePruebaRepository;
import com.mingeso.topeducation_ms3.repositories.ExamenRepository;
import com.mingeso.topeducation_ms3.repositories.InteresMesesAtrasoRepository;
import com.mingeso.topeducation_ms3.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculosAdministrativosService {
    ExamenRepository examenRepository;
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    InteresMesesAtrasoRepository interesMesesAtrasoRepository;
    RazonService razonService;
    EstudianteService estudianteService;
    PagoService pagoService;

    @Autowired
    public CalculosAdministrativosService(
            ExamenRepository examenRepository,
            DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
            InteresMesesAtrasoRepository interesMesesAtrasoRepository,
            RazonService razonService,
            EstudianteService estudianteService,
            PagoService pagoService
            ){
        this.examenRepository = examenRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.interesMesesAtrasoRepository = interesMesesAtrasoRepository;
        this.razonService = razonService;
        this.estudianteService = estudianteService;
        this.pagoService = pagoService;
    }

    @Transactional
    public List<RazonDTO> calcularPlanilla(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioProceso = Util.obtenerFechaInicioProceso(fechaActual);
        LocalDate fechaExamenesProceso = Util.obtenerFechaExamenesProceso(fechaInicioProceso);
//        if(fechaActual.isBefore(fechaExamenesProceso) || fechaActual.isAfter(fechaInicioProceso.minusDays(1))){
//            throw new FechaNoPermitidaException("El cálculo de planilla del proceso correspondiente debe ser realizado"
//                    + " entre el último viernes del més (cuando se rindieron los últimos exámenes) " + fechaExamenesProceso
//                    + " y el inicio de pago de arancel del proceso "
//                    + fechaInicioProceso + ".");
//        }

        aplicarDescuentosPorPuntajes(fechaActual);

        aplicarInteresesPorMesesAtraso(fechaActual);

        return razonService.obtenerRazones();
    }

    @Transactional
    public void aplicarDescuentosPorPuntajes(LocalDate fechaActual){
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

    public void aplicarInteresesPorMesesAtraso(LocalDate fechaActual){
        LocalDate fechaInicioProceso = Util.obtenerFechaInicioProceso(fechaActual);
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantes();
        Map<String, List<RazonDTO>> cuotasConInteres = new HashMap<>();
        List<RazonDTO> razones = razonService.obtenerRazones();
        List<RazonDTO> razonesActualizadas = new ArrayList<>();
        for(EstudianteDTO estudiante : estudiantes){
            List<RazonDTO> razonesEstudiante = razones
                    .stream()
                    .filter(razon -> razon.getIdEstudiante().equals(estudiante.getId()))
                    .toList();
            //obtener razones que sean de tipo arancel, estén pendientes o atrasadas y que sean anteriores a la fecha del proceso.
            List<RazonDTO> cuotasArancelPendientesOAtrasadas = razonesEstudiante.stream().filter(razon ->
                    (razon.getTipo().getId().equals(2))
                            && (razon.getEstado().getId().equals(2) || razon.getEstado().getId().equals(3))
            ).toList();

            List<RazonDTO> cuotasArancelAntiguasPendientesOAtrasadas = cuotasArancelPendientesOAtrasadas.stream().filter(razon ->
                    (razon.getFechaFin().isBefore(fechaInicioProceso))
            ).toList();

            List<Integer> porcentajesInteres = obtenerPorcentajesInteres(cuotasArancelAntiguasPendientesOAtrasadas, fechaInicioProceso);

            //Aplicar intereses
            for(RazonDTO cuota : cuotasArancelPendientesOAtrasadas){
                for(Integer porcentajeInteres : porcentajesInteres){
                    cuota.setMonto(cuota.getMonto() + ((cuota.getMonto() * porcentajeInteres) / 100));
                }
                razonesActualizadas.add(cuota);
            }
        }
        razonService.actualizarCuotas(razonesActualizadas);
    }

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

    public List<Integer> obtenerPorcentajesInteres(List<RazonDTO> cuotasArancelAntiguasPendientesOAtrasadas, LocalDate fechaInicioProceso){
        List<Integer> porcentajesInteres = new ArrayList<>();

        for(RazonDTO cuota : cuotasArancelAntiguasPendientesOAtrasadas){
            //Si está pendiente, pasa a atrasada
            if(cuota.getEstado().getId() == 2) razonService.actualizarEstadoRazon(cuota.getId(), 3);

            Period periodo = Period.between(cuota.getFechaInicio(), fechaInicioProceso);
            //usualmente los años son 0 y se reduce a solo la diferencia en meses.
            Integer mesesAtraso = periodo.getYears() * 12 + periodo.getMonths();
            Integer porcentajeInteres = interesMesesAtrasoRepository.findInteresByMesesAtraso(mesesAtraso);
            porcentajesInteres.add(porcentajeInteres);
        }

        return porcentajesInteres;
    }

    public List<EntradaReporteResumen> calcularReporteResumen(){
        List<EntradaReporteResumen> reporte = new ArrayList<>();
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantes();
        if(estudiantes.isEmpty()) throw new RegistroNoExisteException("No existen estudiantes registrados.");
        List<RazonDTO> razones = razonService.obtenerRazones();
        List<PagoDTO> pagos = pagoService.obtenerPagos();
        List<Examen> examenes = examenRepository.findAll();

        for(EstudianteDTO estudiante : estudiantes){
            List<RazonDTO> razonesEstudiante = razones
                    .stream()
                    .filter(razon -> razon.getIdEstudiante().equals(estudiante.getId()))
                    .toList();

            List<PagoDTO> pagosEstudiante = pagos
                    .stream()
                    .filter(pago -> pago.getIdEstudiante().equals(estudiante.getId()))
                    .toList();

            List<Examen> examenesEstudiante = examenes
                    .stream()
                    .filter(examen -> examen.getIdEstudiante().equals(estudiante.getId()))
                    .toList();

            EntradaReporteResumen entrada = EntradaReporteResumen.builder()
                    .rut(estudiante.getRut())
                    .numeroExamenesRendidos(examenes.size())
                    .promedioExamenes(calcularPromedioExamenes(examenes))
                    .totalArancel(calcularTotalArancel(razonesEstudiante))
                    .tipoPago(estudiante.getTipoPagoArancel().getTipo())
                    .numeroCuotasPactadas(estudiante.getCuotasPactadas())
                    .numeroCuotasPagadas(calcularNumeroCuotasPagadas(razonesEstudiante))
                    .arancelPagado(calcularArancelPagado(razonesEstudiante))
                    .totalPagado(calcularTotalPagado(razonesEstudiante))
                    .fechaUltimoPago(calcularFechaUltimoPago(pagosEstudiante))
                    .saldoArancelPendiente(calcularArancelPendiente(razonesEstudiante))
                    .saldoTotalPendiente(calcularTotalPendiente(razonesEstudiante))
                    .numeroCuotasAtrasadas(calcularNumeroCuotasAtrasadas(razonesEstudiante))
                    .build();
            reporte.add(entrada);
        }
        return reporte;
    }


    private int calcularPromedioExamenes(List<Examen> examenes){
        int promedioExamenes = 0;
        if(examenes.isEmpty()) return 0;
        for(Examen examen : examenes){
            if(examen.getPuntaje() == null) examen.setPuntaje(0);
            promedioExamenes += examen.getPuntaje();
        }
        promedioExamenes /= examenes.size();
        return promedioExamenes;
    }

    private int calcularTotalArancel(List<RazonDTO> razones){
        int totalArancel = 0;
        int anioActual = LocalDate.now().getYear();
        for(RazonDTO razon : razones){
            //Si es arancel calcular total
            if((razon.getTipo().getId() == 2)
                    && ((razon.getFechaInicio().getYear() == anioActual)
                    || ((razon.getFechaInicio().getYear() - anioActual) == 1
                    && razon.getFechaInicio().getMonth().equals(Month.JANUARY)))
            ){
                totalArancel += razon.getMonto();
            }
        }
        return totalArancel;
    }

    private int calcularTotalPendiente(List<RazonDTO> razones){
        int totalPendiente = 0;
        for(RazonDTO razon : razones){
            //Acá no hay restricción de año ya que puede ser un arancel con interes acumulado de años con una deuda millonaria :p.
            //se busca todos los pendientes y atrasados
            if(((razon.getEstado().getId() == 2) || (razon.getEstado().getId() == 3))){
                totalPendiente += razon.getMonto();
            }
        }
        return totalPendiente;
    }

    private int calcularArancelPendiente(List<RazonDTO> razones){
        int arancelPendiente = 0;
        for(RazonDTO razon : razones){
            //Acá no hay restricción de año ya que puede ser un arancel con interes acumulado de años con una deuda millonaria :p.
            if((razon.getTipo().getId() == 2)
                    && ((razon.getEstado().getId() == 2) || (razon.getEstado().getId() == 3))){
                arancelPendiente += razon.getMonto();
            }
        }
        return arancelPendiente;
    }

    private int calcularTotalPagado(List<RazonDTO> razones){
        int totalPagado = 0;
        int anioActual = LocalDate.now().getYear();
        for(RazonDTO razon : razones){
            if(((razon.getFechaInicio().getYear() == anioActual)
                    || ((razon.getFechaInicio().getYear() - anioActual) == 1
                    && razon.getFechaInicio().getMonth().equals(Month.JANUARY)))
                    && (razon.getEstado().getId() == 1)){
                totalPagado += razon.getMonto();
            }
        }
        return totalPagado;
    }

    private int calcularArancelPagado(List<RazonDTO> razones){
        int arancelPagado = 0;
        int anioActual = LocalDate.now().getYear();
        for(RazonDTO razon : razones){
            if((razon.getTipo().getId() == 2)
                    && ((razon.getFechaInicio().getYear() == anioActual)
                    || ((razon.getFechaInicio().getYear() - anioActual) == 1
                    && razon.getFechaInicio().getMonth().equals(Month.JANUARY)))
                    && (razon.getEstado().getId() == 1)){
                arancelPagado += razon.getMonto();
            }
        }
        return arancelPagado;
    }

    private int calcularNumeroCuotasPagadas(List<RazonDTO> razones){
        int numeroCuotasPagadas = 0;
        int anioActual = LocalDate.now().getYear();
        for(RazonDTO razon : razones){
            if((razon.getTipo().getId() == 2)
                    && ((razon.getFechaInicio().getYear() == anioActual)
                    || ((razon.getFechaInicio().getYear() - anioActual) == 1
                    && razon.getFechaInicio().getMonth().equals(Month.JANUARY)))
                    && (razon.getEstado().getId() == 1)){
                numeroCuotasPagadas += 1;
            }
        }
        return numeroCuotasPagadas;
    }

    private LocalDate calcularFechaUltimoPago(List<PagoDTO> pagos){
        if(pagos.size() < 1) return null;
        LocalDate fechaUltimoPago = pagos.get(0).getFecha();
        for(PagoDTO pago : pagos){
            if(pago.getFecha().isAfter(fechaUltimoPago)){
                fechaUltimoPago = pago.getFecha();
            }
        }
        return fechaUltimoPago;
    }

    private int calcularNumeroCuotasAtrasadas(List<RazonDTO> razones){
        int numeroCuotasAtrasadas = 0;
        for(RazonDTO razon : razones){
            //También se elimina restricción de año
            if((razon.getTipo().getId() == 2)
                    && (razon.getEstado().getId() == 3)){
                numeroCuotasAtrasadas += 1;
            }
        }
        return numeroCuotasAtrasadas;
    }
}
