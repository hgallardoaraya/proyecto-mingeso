package com.mingeso.topeducation;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.repositories.*;
import com.mingeso.topeducation.services.RazonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RazonServiceTest {
    @Mock
    RazonRepository razonRepository;
    @Mock
    TipoRazonRepository tipoRazonRepository;
    @Mock
    EstadoRazonRepository estadoRazonRepository;
    @Mock
    EstudianteRepository estudianteRepository;
    @Mock
    TotalRazonRepository totalRazonRepository;
    @Mock
    DescuentoTipoPagoArancelRepository descuentoTipoPagoArancelRepository;
    @Mock
    DescuentoTipoColegioRepository descuentoTipoColegioRepository;
    @Mock
    DescuentoAnioEgresoRepository descuentoAnioEgresoRepository;
    @Mock
    DescuentoPuntajePruebaRepository descuentoPuntajePruebaRepository;
    @Mock
    ExamenRepository examenRepository;
    @Mock
    InteresMesesAtrasoRepository interesMesesAtrasoRepository;
    @Mock
    MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;
    @InjectMocks
    RazonService razonService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    void generarCuotasTest(){
        Estudiante estudiante = crearEstudiantePorDefecto();

        when(estudianteRepository.findByRut(estudiante.getRut())).thenReturn(Optional.of(estudiante));
        //colegio municiapl
        when(maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.getTipoColegio().getId())).thenReturn(10);
        when(totalRazonRepository.findTotalByTipoRazon("MATRICULA")).thenReturn(70000);
        when(totalRazonRepository.findTotalByTipoRazon("ARANCEL")).thenReturn(1500000);
        //paga en cuotas, tiene 0 descuento por este motivo
        when(descuentoTipoPagoArancelRepository.findDescuentoByIdTipoPagoArancel(estudiante.getTipoPagoArancel().getId())).thenReturn(0);
        when(descuentoTipoColegioRepository.findDescuentoByIdTipoColegio(estudiante.getTipoColegio().getId())).thenReturn(20);
        when(descuentoAnioEgresoRepository.findDescuentoByAnioEgreso(estudiante.getAnioEgreso())).thenReturn(4);

        TipoRazon tipoMatricula = new TipoRazon();
        tipoMatricula.setId(0);
        tipoMatricula.setTipo("MATRICULA");

        EstadoRazon estadoPendiente = new EstadoRazon();
        estadoPendiente.setId(1);
        estadoPendiente.setEstado("PENDIENTE");

        when(tipoRazonRepository.findById(0)).thenReturn(Optional.of(tipoMatricula));
        when(estadoRazonRepository.findById(1)).thenReturn(Optional.of(estadoPendiente));
        when(razonRepository.save(ArgumentMatchers.any(Razon.class))).thenAnswer(invocation -> invocation.getArgument(0, Razon.class));


        TipoRazon tipoArancel = new TipoRazon();
        tipoMatricula.setId(1);
        tipoMatricula.setTipo("ARANCEL");
        when(tipoRazonRepository.findById(1)).thenReturn(Optional.of(tipoArancel));

        List<Razon> resultados = razonService.generarCuotas(estudiante.getRut(), 2);

        Razon matriculaEsperada = new Razon(0, 70000, LocalDate.of(LocalDate.now().getYear(), Month.MARCH, 1).minusDays(5), LocalDate.of(LocalDate.now().getYear(), Month.MARCH, 1), tipoMatricula, estadoPendiente, estudiante);
        Razon arancel1Esperado = new Razon(1, 570000, LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 5), LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 10), tipoArancel, estadoPendiente, estudiante);
        Razon arancel2Esperado = new Razon(2, 570000, LocalDate.of(LocalDate.now().getYear(), Month.MAY, 5), LocalDate.of(LocalDate.now().getYear(), Month.MAY, 10), tipoArancel, estadoPendiente, estudiante);

        List<Razon> razonesEsperadas = new ArrayList<>();
        razonesEsperadas.add(matriculaEsperada);
        razonesEsperadas.add(arancel1Esperado);
        razonesEsperadas.add(arancel2Esperado);

        assertEquals(razonesEsperadas, resultados);
    }

    private Estudiante crearEstudiantePorDefecto(){
        Estudiante estudiante = new Estudiante();
        estudiante.setRut("11.222.333-4");
        estudiante.setNombre1("NombreTest 1");
        estudiante.setNombre2("NombreTest 2");
        estudiante.setApellido1("ApellidoTest 1");
        estudiante.setApellido2("ApellidoTest 2");
        estudiante.setFechaNacimiento(LocalDate.of(1999, 1, 1));
        estudiante.setAnioEgreso(2018);
        estudiante.setNombreColegio("Colegio Test");

        TipoPagoArancel pagoEnCuotas = new TipoPagoArancel();
        pagoEnCuotas.setId(0);
        pagoEnCuotas.setTipo("CUOTAS");
        estudiante.setTipoPagoArancel(pagoEnCuotas);

        TipoColegio tipoMunicipal = new TipoColegio();
        tipoMunicipal.setId(0);
        tipoMunicipal.setTipo("MUNICIPAL");
        estudiante.setTipoColegio(tipoMunicipal);

        return estudiante;
    }
}
