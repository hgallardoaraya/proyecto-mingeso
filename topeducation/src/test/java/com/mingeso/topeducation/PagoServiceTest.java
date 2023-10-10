package com.mingeso.topeducation;

import com.mingeso.topeducation.entities.*;
import com.mingeso.topeducation.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation.exceptions.ValorFueraDeRangoException;
import com.mingeso.topeducation.repositories.EstadoRazonRepository;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.PagoRepository;
import com.mingeso.topeducation.repositories.RazonRepository;
import com.mingeso.topeducation.requests.RegistrarPagoRequest;
import com.mingeso.topeducation.services.PagoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {
    @Mock
    PagoRepository pagoRepository;
    @Mock
    EstudianteRepository estudianteRepository;
    @Mock
    RazonRepository razonRepository;
    @Mock
    EstadoRazonRepository estadoRazonRepository;
    @InjectMocks
    PagoService pagoService;
    private Estudiante estudiante;

    @BeforeEach
    public void setUp() {
        estudiante = new Estudiante();
        estudiante.setRut("11.222.333-4");
        estudiante.setNombre1("NombreTest 1");
        estudiante.setNombre2("NombreTest 2");
        estudiante.setApellido1("ApellidoTest 1");
        estudiante.setApellido2("ApellidoTest 2");
        estudiante.setFechaNacimiento(LocalDate.of(1999, 1, 1));
        estudiante.setAnioEgreso(2018);
        estudiante.setNombreColegio("Colegio Test");
    }

    @Transactional
    @Test
    void testRegistrarPago() {
        // Mock de objetos necesarios
        Estudiante estudiante = new Estudiante();
        estudiante.setRut("123456789");
        when(estudianteRepository.findByRut("123456789")).thenReturn(Optional.of(estudiante));

        Razon razon1 = new Razon();
        razon1.setId(1);
        razon1.setMonto(100);
        when(razonRepository.findById(1)).thenReturn(Optional.of(razon1));

        Razon razon2 = new Razon();
        razon2.setId(2);
        razon2.setMonto(300);
        when(razonRepository.findById(2)).thenReturn(Optional.of(razon2));

        EstadoRazon estadoPagada = new EstadoRazon();
        estadoPagada.setId(0);
        when(estadoRazonRepository.findById(0)).thenReturn(Optional.of(estadoPagada));

        RegistrarPagoRequest request = new RegistrarPagoRequest();
        request.setRut("123456789");
        Integer[] idsRazones = {1, 2};
        request.setIdsRazones(idsRazones);

        // Configura el comportamiento de pagoRepository.save para devolver el objeto pagodb
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0, Pago.class));

        Pago pago = pagoService.registrarPago(request);

        System.out.println(pago);
        // Verificación de resultados
        assertNotNull(pago);
        assertEquals(estudiante, pago.getEstudiante());
        assertEquals(400, pago.getTotal()); // La suma de los montos de razon1 y razon2
        assertEquals(LocalDate.now(), pago.getFecha());
        assertEquals(2, pago.getRazones().size()); // Deben haberse agregado dos razones a la lista de razones
        assertTrue(pago.getRazones().contains(razon1));
        assertTrue(pago.getRazones().contains(razon2));
    }

    @Transactional
    @Test
    void testRegistrarPagoNoExistenRazones() {
        // Mock de objetos necesarios
        Estudiante estudiante = new Estudiante();
        estudiante.setRut("123456789");
        when(estudianteRepository.findByRut("123456789")).thenReturn(Optional.of(estudiante));

        RegistrarPagoRequest request = new RegistrarPagoRequest();
        request.setRut("123456789");
        Integer[] idsRazones = {};
        request.setIdsRazones(idsRazones);

        assertThrows(RegistroNoExisteException.class, () -> pagoService.registrarPago(request));

        verify(pagoRepository, never()).save(any(Pago.class));
    }

    @Transactional
    @Test
    void testRegistrarPagoMontoNegativo() {
        // Mock de objetos necesarios
        Estudiante estudiante = new Estudiante();
        estudiante.setRut("123456789");
        when(estudianteRepository.findByRut("123456789")).thenReturn(Optional.of(estudiante));

        Razon razon = new Razon();
        razon.setId(2);
        razon.setMonto(-500);
        when(razonRepository.findById(2)).thenReturn(Optional.of(razon));

        RegistrarPagoRequest request = new RegistrarPagoRequest();
        request.setRut("123456789");
        Integer[] idsRazones = {2};
        request.setIdsRazones(idsRazones);

        assertThrows(ValorFueraDeRangoException.class, () -> pagoService.registrarPago(request));

        verify(razonRepository, never()).save(any(Razon.class));
        verify(pagoRepository, never()).save(any(Pago.class));
    }


}
