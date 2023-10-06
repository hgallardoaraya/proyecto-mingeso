package com.mingeso.topeducation.services;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.EstadoRazonRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.PagoRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import com.mingeso.topeducation.requests.RegistrarPagoRequest;
import com.mingeso.topeducation.utils.EntradaReporteResumen;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final EstudianteRepository estudianteRepository;
    private final RazonRepository razonRepository;
    private final EstadoRazonRepository estadoRazonRepository;
    @Autowired
    public PagoService(PagoRepository pagoRepository, EstudianteRepository estudianteRepository, RazonRepository razonRepository, EstadoRazonRepository estadoRazonRepository){
        this.pagoRepository = pagoRepository;
        this.estudianteRepository = estudianteRepository;
        this.razonRepository = razonRepository;
        this.estadoRazonRepository = estadoRazonRepository;
    }

    //Existe un pago que contiene todas las razones que se desean pagar.
    @Transactional
    public void registrarPago(RegistrarPagoRequest request) {
        try{
            Pago pago = new Pago();
            Estudiante estudiante = estudianteRepository.findByRut(request.getRut());
            pago.setEstudiante(estudiante);
            if(estudiante == null) throw new RuntimeException("Error, el estudiante no existe.");

            // Obtén la fecha actual
            LocalDate fechaActual = LocalDate.now();

            List<Razon> razones = new ArrayList<>();
            Integer total = 0;
            for(Integer idRazon : request.getIdsRazones()){
                Razon razon = razonRepository.findById(idRazon).get();
                //estado "PAGADA"
                EstadoRazon estadoRazon = estadoRazonRepository.findById(0).get();
                razon.setEstado(estadoRazon);
                razones.add(razon);
                razonRepository.save(razon);
                total += razon.getMonto();
            }
            if(razones == null) throw new RuntimeException("Error no se indicaron o no existen razones.");
            pago.setRazones(razones);
            pago.setTotal(total);
            pago.setFecha(LocalDate.now());
            pagoRepository.save(pago);
        }catch(Exception e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public ArrayList<Razon> obtenerRazonesAPagar(String rut) {
        return razonRepository.findAllPendientesByRut(rut);
    }

    /*▪ RUT estudiante <Listo>
    ▪ Nombre del estudiante <Listo>
    ▪ Nro. exámenes rendidos <Listo>
    ▪ Promedio puntaje exámenes <Listo>
    ▪ Monto total arancel a pagar <Listo>
    ▪ Tipo Pago (Contado/Cuotas) <Listo>
    ▪ Nro. total de cuotas pactadas <Listo>
    ▪ Nro. cuotas pagadas
    ▪ Monto total pagado
    ▪ Fecha último pago
    ▪ Saldo por pagar
    ▪ Nro. Cuotas con retraso*/
    public List<EntradaReporteResumen> calcularReporteResumen(){
        try{
            List<EntradaReporteResumen> reporte = new ArrayList<>();
            List<Estudiante> estudiantes = estudianteRepository.findAll();
            for(Estudiante estudiante : estudiantes){
                List<Razon> razones = estudiante.getRazones();
                List<Pago> pagos = estudiante.getPagos();
                List<Examen> examenes = estudiante.getExamenes();
                EntradaReporteResumen entrada = EntradaReporteResumen.builder()
                        .rut(estudiante.getRut())
                        .numeroExamenesRendidos(examenes.size())
                        .promedioExamenes(calcularPromedioExamenes(examenes))
                        .totalArancel(calcularTotalArancel(razones))
                        .tipoPago(estudiante.getTipoPagoArancel().getTipo())
                        .numeroCuotasPactadas(calcularNumeroCuotasPactadas(razones))
                        .numeroCuotasPagadas(calcularNumeroCuotasPagadas(razones))
                        .arancelPagado(calcularArancelPagado(razones))
                        .totalPagado(calcularTotalPagado(razones))
                        .fechaUltimoPago(calcularFechaUltimoPago(pagos))
                        .saldoArancelPendiente(calcularArancelPendiente(razones))
                        .saldoTotalPendiente(calcularTotalPendiente(razones))
                        .numeroCuotasAtrasadas(calcularNumeroCuotasAtrasadas(razones))
                        .build();
                reporte.add(entrada);
            }
            System.out.println(reporte.toString());
            return reporte;
        }catch(Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private Integer calcularNumeroCuotasAtrasadas(List<Razon> razones){
        Integer numeroCuotasAtrasadas = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            //También se elimina restricción de año
            if((razon.getTipo().getId() == 1)
                    && (razon.getEstado().getId() == 2)){
                numeroCuotasAtrasadas += 1;
            }
        }
        return numeroCuotasAtrasadas;
    }

    private Integer calcularTotalPendiente(List<Razon> razones){
        Integer totalPendiente = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            //Acá no hay restricción de año ya que puede ser un arancel con interes acumulado de años con una deuda millonaria :p.
            if(((razon.getEstado().getId() == 1) || (razon.getEstado().getId() == 2))){
                totalPendiente += razon.getMonto();
            }
        }
        return totalPendiente;
    }

    private Integer calcularArancelPendiente(List<Razon> razones){
        Integer arancelPendiente = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            //Acá no hay restricción de año ya que puede ser un arancel con interes acumulado de años con una deuda millonaria :p.
            if((razon.getTipo().getId() == 1)
                    && ((razon.getEstado().getId() == 1) || (razon.getEstado().getId() == 2))){
                arancelPendiente += razon.getMonto();
            }
        }
        return arancelPendiente;
    }

    private LocalDate calcularFechaUltimoPago(List<Pago> pagos){
        Integer anioActual = LocalDate.now().getYear();
        LocalDate fechaUltimoPago = pagos.get(0).getFecha();
        for(Pago pago : pagos){
            if(pago.getFecha().isAfter(fechaUltimoPago)){
                fechaUltimoPago = pago.getFecha();
            }
        }
        return fechaUltimoPago;
    }

    private Integer calcularTotalPagado(List<Razon> razones){
        Integer totalPagado = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            if((razon.getFechaInicio().getYear() == anioActual)
                    && (razon.getEstado().getId() == 0)){
                totalPagado += razon.getMonto();
            }
        }
        return totalPagado;
    }

    private Integer calcularArancelPagado(List<Razon> razones){
        Integer arancelPagado = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            if((razon.getTipo().getId() == 1)
                    && (razon.getFechaInicio().getYear() == anioActual)
                    && (razon.getEstado().getId() == 0)){
                arancelPagado += razon.getMonto();
            }
        }
        return arancelPagado;
    }

    private Integer calcularNumeroCuotasPagadas(List<Razon> razones){
        Integer numeroCuotasPagadas = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            if((razon.getTipo().getId() == 1)
                    && (razon.getFechaInicio().getYear() == anioActual)
                    && (razon.getEstado().getId() == 0)){
                numeroCuotasPagadas += 1;
            }
        }
        return numeroCuotasPagadas;
    }

    private Integer calcularNumeroCuotasPactadas(List<Razon> razones){
        Integer numeroCuotasPactadas = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            if((razon.getTipo().getId() == 1) && (razon.getFechaInicio().getYear() == anioActual)){
                numeroCuotasPactadas += 1;
            }
        }
        return numeroCuotasPactadas;
    }

    private Integer calcularTotalArancel(List<Razon> razones){
        Integer totalArancel = 0;
        Integer anioActual = LocalDate.now().getYear();
        for(Razon razon : razones){
            //Si es arancel calcular total
            if((razon.getTipo().getId() == 1) && (razon.getFechaInicio().getYear() == anioActual)){
               totalArancel += razon.getMonto();
            }
        }
        return totalArancel;
    }

    private Integer calcularPromedioExamenes(List<Examen> examenes){
        Integer promedioExamenes = 0;
        for(Examen examen : examenes){
            promedioExamenes += examen.getPuntaje();
        }
        promedioExamenes /= examenes.size();
        return promedioExamenes;
    }
}

