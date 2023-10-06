package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
                 ExamenRepository examenRepository
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
    }

    @Transactional
    public void generarCuotas(String rut, Integer numCuotas){
        try{
            Estudiante estudiante = estudianteRepository.findByRut(rut);

            Integer totalMatricula = totalRazonRepository.findTotalByTipoRazon("MATRICULA");
            Integer totalArancel = totalRazonRepository.findTotalByTipoRazon("ARANCEL");

            //Descuento por pagar en cuotas es 0, pero podría cambiar.
            Integer porcentajeDescuento = descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.getTipoPagoArancel().getId());

            //Si paga en cuotas o al contado.
            if(estudiante.getTipoPagoArancel().getId() == 0){
                porcentajeDescuento += descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.getTipoColegio().getId());
                porcentajeDescuento += descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.getAnioEgreso());
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
            Razon matricula = new Razon(0, totalMatricula, fechaInicioMatricula, fechaInicioClases, tipoMatricula, estadoPendiente, estudiante, false);
            razonRepository.save(matricula);

            //Generar cuotas arancel
            LocalDate fechaInicioArancel = LocalDate.of(fechaActual.getYear(), Month.MARCH, 5);
            LocalDate fechaFinArancel = LocalDate.of(fechaActual.getYear(), Month.MARCH, 10);
            Integer cuota = totalArancel / numCuotas;
            TipoRazon tipoArancel = tipoRazonRepository.findById(1).get();

            for(int i = 0; i < numCuotas; i++){
                Integer numero = i + 1;
                Razon arancel = new Razon(numero, cuota, fechaInicioArancel, fechaFinArancel, tipoArancel, estadoPendiente, estudiante, false);
                razonRepository.save(arancel);
                fechaInicioArancel = fechaInicioArancel.plusMonths(1);
                fechaFinArancel = fechaFinArancel.plusMonths(1);
            }

        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public ArrayList<Razon> getRazones(String rut) {
        return razonRepository.findAllByRut(rut);
    }

    @Transactional
    public void calcularPlanilla(){
        try{
            List<Examen> examenes = examenRepository.findAllSinRevision();

            Map<String, List<Integer>> rutPuntajes = new HashMap<>();

            //aplicar descuentos examenes
            for(Examen examen : examenes){
                if(rutPuntajes.containsKey(examen.getEstudiante().getRut())){
                    rutPuntajes.get(examen.getEstudiante().getRut()).add(examen.getPuntaje());
                }else{
                    List<Integer> nuevaListaPuntajes = new ArrayList<>();
                    nuevaListaPuntajes.add(examen.getPuntaje());
                    rutPuntajes.put(examen.getEstudiante().getRut(), nuevaListaPuntajes);
                }
                //actualizar estado de revision del examen
                examen.setRevision(true);
                examenRepository.save(examen);
            }

            for(String rut : rutPuntajes.keySet()){
                Integer total = rutPuntajes.get(rut).stream().reduce(0, Integer::sum);
                Integer promedio = total/rutPuntajes.get(rut).size();
                Integer porcentajeDescuento = descuentoPuntajePruebaRepository.findDescuentoByPuntaje(promedio);
                List<Razon> cuotasArancel = razonRepository.findAllPendientesByRut(rut);
                for(Razon cuota : cuotasArancel){
                    //aplicar descuento
                    cuota.setMonto(cuota.getMonto() - ((cuota.getMonto() * porcentajeDescuento) / 100));
                    razonRepository.save(cuota);
                }
            }

            //Crear intereses
            List<Razon> cuotasArancel = razonRepository.findAllPendientes();
            LocalDate fechaActual = LocalDate.now();
            Razon cuotaProceso = razonRepository.findCuotaProceso(fechaActual);
            LocalDate fechaFinProceso = cuotaProceso.getFechaFin();

            if(cuotaProceso.getCalculoPlanillaRealizado()){
                throw new Exception("El cálculo de planilla ya fue realizado para el próximo proceso en marcha en la fecha "
                + fechaFinProceso);
            }

            List<Integer> porcentajesInteres = new ArrayList<>();

            for(Razon cuota : cuotasArancel){
                if(cuota.getFechaFin().isBefore(fechaFinProceso)){
                    //si es anterior al proceso actual y está pendiente, pasa a atrasada
                    if(cuota.getEstado().getId() == 1){
                        EstadoRazon estadoCuotaAtrasada = estadoRazonRepository.findById(2).get();
                        cuota.setEstado(estadoCuotaAtrasada);
                        razonRepository.save(cuota);
                    }

                    //si está atrasada, se calcula su interes
                    if(cuota.getEstado().getId() == 2){
                        Period periodo = Period.between(cuota.getFechaFin(), fechaFinProceso);
                        int mesesAtraso = periodo.getYears() * 12 + periodo.getMonths();
                        Integer porcentajeInteres = interesMesesAtrasoRepository.findInteresByMesesAtraso(mesesAtraso);
                        porcentajesInteres.add(porcentajeInteres);
                    }
                }
            }

            //se aplican los intereses
            for(Razon cuota : cuotasArancel){
                for(Integer porcentajeInteres : porcentajesInteres){
                    cuota.setMonto(cuota.getMonto() + ((cuota.getMonto() * porcentajeInteres) / 100));
                    cuota.setCalculoPlanillaRealizado(true);
                    razonRepository.save(cuota);
                }
            }
        }catch(Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
