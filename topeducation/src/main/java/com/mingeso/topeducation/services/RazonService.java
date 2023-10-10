package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;

@Service
public class RazonService {
    RazonRepository razonRepository;
    TipoRazonRepository tipoRazonRepository;
    EstadoRazonRepository estadoRazonRepository;
    EstudianteRepository estudianteRepository;
    TotalRazonRepository totalRazonRepository;
    DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository;
    DescuentoTipoColegioRepository descuentoTipoColegioRepository;
    DescuentoAnioEgresoRepository descuentoAnioEgresoRepository;
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    ExamenRepository examenRepository;
    InteresMesesAtrasoRepository interesMesesAtrasoRepository;
    MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;

    @Autowired
    RazonService(RazonRepository razonRepository,
                 TipoRazonRepository tipoRazonRepository,
                 EstadoRazonRepository estadoRazonRepository,
                 EstudianteRepository estudianteRepository,
                 TotalRazonRepository totalRazonRepository,
                 DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository,
                 DescuentoTipoColegioRepository descuentoTipoColegioRepository,
                 DescuentoAnioEgresoRepository descuentoAnioEgresoRepository,
                 DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository,
                 InteresMesesAtrasoRepository interesMesesAtrasoRepository,
                 ExamenRepository examenRepository,
                 MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository
                 ){
        this.razonRepository = razonRepository;
        this.tipoRazonRepository = tipoRazonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
        this.estudianteRepository = estudianteRepository;
        this.totalRazonRepository = totalRazonRepository;
        this.descuentoTipoPagoArancelRepository = descuentoTipoPagoArancelRepository;
        this.descuentoTipoColegioRepository = descuentoTipoColegioRepository;
        this.descuentoAnioEgresoRepository = descuentoAnioEgresoRepository;
        this.descuentoPuntajePruebaRepository = descuentoPuntajePruebaRepository;
        this.examenRepository = examenRepository;
        this.interesMesesAtrasoRepository = interesMesesAtrasoRepository;
        this.maxCuotasTipoColegioRepository = maxCuotasTipoColegioRepository;
    }

    @Transactional
    public void generarCuotas(String rut, Integer numCuotas){

        Optional<Estudiante> estudiante = estudianteRepository.findByRut(rut);

        if(estudiante.isEmpty()) throw new RegistroNoExisteException("El estudiante con rut " + rut + " no existe.");
        if(numCuotas < 0) throw new ValorFueraDeRangoException("El número de cuotas es menor a 0.");
        if(numCuotas > maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.get().getTipoColegio().getId())) throw new ValorFueraDeRangoException("El número de cuotas excede el máximo.");

        Integer totalMatricula = totalRazonRepository.findTotalByTipoRazon("MATRICULA");
        Integer totalArancel = totalRazonRepository.findTotalByTipoRazon("ARANCEL");

        //Descuento por pagar en cuotas es 0, pero podría cambiar.
        Integer porcentajeDescuento = descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.get().getTipoPagoArancel().getId());

        //Si paga en cuotas
        if(estudiante.get().getTipoPagoArancel().getId() == 0){
            porcentajeDescuento += descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.get().getTipoColegio().getId());
            porcentajeDescuento += descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.get().getAnioEgreso());
        }

        //Aplicar porcentaje descuentos
        totalArancel = totalArancel - ((totalArancel * porcentajeDescuento) / 100);

        // Obtén la fecha actual
        LocalDate fechaActual = LocalDate.now();

        //cambiar esto a variable en bdd
        LocalDate fechaInicioClases = LocalDate.of(fechaActual.getYear(), Month.MARCH, 1);
        LocalDate fechaInicioMatricula = fechaInicioClases.minusDays(5);


        TipoRazon tipoMatricula = tipoRazonRepository.findById(0).get();
        EstadoRazon estadoPendiente = estadoRazonRepository.findById(1).get();

        // Matricula
        Razon matricula = new Razon(0, totalMatricula, fechaInicioMatricula, fechaInicioClases, tipoMatricula, estadoPendiente, estudiante.get());
        razonRepository.save(matricula);

        //Generar cuotas arancel
        LocalDate fechaInicioArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 5);
        LocalDate fechaFinArancel = LocalDate.of(fechaInicioClases.getYear(), fechaInicioClases.getMonth().plus(1), 10);

        Integer cuota = totalArancel / numCuotas;
        TipoRazon tipoArancel = tipoRazonRepository.findById(1).get();

        for(int i = 0; i < numCuotas; i++){
            Integer numero = i + 1;
            Razon arancel = new Razon(numero, cuota, fechaInicioArancel, fechaFinArancel, tipoArancel, estadoPendiente, estudiante.get());
            razonRepository.save(arancel);
            fechaInicioArancel = fechaInicioArancel.plusMonths(1);
            fechaFinArancel = fechaFinArancel.plusMonths(1);
        }
    }

    public ArrayList<Razon> getRazones(String rut) {
        return razonRepository.findAllByRut(rut);
    }

    @Transactional
    public void calcularPlanilla(){

        List<Examen> examenes = examenRepository.findAllSinRevision();

        Map<String, List<Integer>> rutPuntajes = obtenerPuntajesPorRut(examenes);

        actualizarRevisionExamenes(examenes);

        aplicarDescuentosPorPuntajes(rutPuntajes);

        aplicarInteresesPorMesesAtraso();

    }

    public Map<String, List<Integer>> obtenerPuntajesPorRut(List<Examen> examenes){
        Map<String, List<Integer>> rutPuntajes = new HashMap<>();

        for(Examen examen : examenes){
            if(rutPuntajes.containsKey(examen.getEstudiante().getRut())){
                rutPuntajes.get(examen.getEstudiante().getRut()).add(examen.getPuntaje());
            }else{
                List<Integer> nuevaListaPuntajes = new ArrayList<>();
                nuevaListaPuntajes.add(examen.getPuntaje());
                rutPuntajes.put(examen.getEstudiante().getRut(), nuevaListaPuntajes);
            }
        }
        return rutPuntajes;
    }

    public void aplicarInteresesPorMesesAtraso(){
        LocalDate fechaInicioProceso = obtenerFechaInicioProceso();
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        for(Estudiante estudiante : estudiantes){
            //obtener razones que sean de tipo arancel, estén pendientes o atrasadas y que sean anteriores a la fecha del proceso.
            List<Razon> cuotasArancelPendientesOAtrasadas = estudiante.getRazones().stream().filter(razon ->
                    (razon.getTipo().getId().equals(1))
                            && (razon.getEstado().getId().equals(1) || razon.getEstado().getId().equals(2))
            ).toList();

            List<Razon> cuotasArancelAntiguasPendientesOAtrasadas = cuotasArancelPendientesOAtrasadas.stream().filter(razon ->
                    (razon.getFechaFin().isBefore(fechaInicioProceso))
            ).toList();

            List<Integer> porcentajesInteres = obtenerPorcentajesInteres(cuotasArancelAntiguasPendientesOAtrasadas, fechaInicioProceso);

//            se aplican los intereses
            for(Razon cuota : cuotasArancelPendientesOAtrasadas){
                for(Integer porcentajeInteres : porcentajesInteres){
                    cuota.setMonto(cuota.getMonto() + ((cuota.getMonto() * porcentajeInteres) / 100));
                }
                razonRepository.save(cuota);
            }
        }


    }

    public void actualizarRevisionExamenes(List<Examen> examenes){
        for(Examen examen : examenes){
            examen.setRevision(true);
            examenRepository.save(examen);
        }
    }

    public void aplicarDescuentosPorPuntajes(Map<String, List<Integer>> rutPuntajes){
        for(String rut : rutPuntajes.keySet()){
            //Calcular total
            Integer total = rutPuntajes.get(rut).stream().reduce(0, Integer::sum);
            //Calcular promedio
            Integer promedio = total/rutPuntajes.get(rut).size();
            Integer porcentajeDescuento = descuentoPuntajePruebaRepository.findDescuentoByPuntaje(promedio);
            List<Razon> cuotasArancel = razonRepository.findCuotaProcesoAndAtrasadasByRut(rut);
            for(Razon cuota : cuotasArancel){
                //aplicar descuento
                cuota.setMonto(cuota.getMonto() - ((cuota.getMonto() * porcentajeDescuento) / 100));
                razonRepository.save(cuota);
            }
        }
    }

    public LocalDate obtenerFechaInicioProceso(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioProceso;
        if(fechaActual.getDayOfMonth() < 10){
            fechaInicioProceso = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), 5);
        }else{
            fechaInicioProceso = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth().plus(1), 5);
        }
        return fechaInicioProceso;
    }

    public List<Integer> obtenerPorcentajesInteres(List<Razon> cuotasArancelAntiguasPendientesOAtrasadas, LocalDate fechaInicioProceso){
        List<Integer> porcentajesInteres = new ArrayList<>();

        for(Razon cuota : cuotasArancelAntiguasPendientesOAtrasadas){
            //Si está pendiente, pasa a atrasada
            if(cuota.getEstado().getId() == 1){
                EstadoRazon estadoCuotaAtrasada = estadoRazonRepository.findById(2).get();
                cuota.setEstado(estadoCuotaAtrasada);
                razonRepository.save(cuota);
            }

            Period periodo = Period.between(cuota.getFechaInicio(), fechaInicioProceso);
            int mesesAtraso = periodo.getYears() * 12 + periodo.getMonths();
            Integer porcentajeInteres = interesMesesAtrasoRepository.findInteresByMesesAtraso(mesesAtraso);
            porcentajesInteres.add(porcentajeInteres);
        }

        return porcentajesInteres;
    }
}
