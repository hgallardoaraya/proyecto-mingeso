package com.mingeso.topeducation;
import com.mingeso.topeducation.entities.Estudiante;
import com.mingeso.topeducation.entities.TipoColegio;
import com.mingeso.topeducation.entities.TipoPagoArancel;
import com.mingeso.topeducation.exceptions.RegistroNoExisteException;
import com.mingeso.topeducation.repositories.EstudianteRepository;
import com.mingeso.topeducation.repositories.MaxCuotasTipoColegioRepository;
import com.mingeso.topeducation.repositories.TipoColegioRepository;
import com.mingeso.topeducation.repositories.TipoPagoArancelRepository;
import com.mingeso.topeducation.requests.IngresarEstudianteRequest;
import com.mingeso.topeducation.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteServiceTest {

    @InjectMocks
    private EstudianteService estudianteService;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private TipoColegioRepository tipoColegioRepository;

    @Mock
    private TipoPagoArancelRepository tipoPagoArancelRepository;

    @Mock
    private MaxCuotasTipoColegioRepository maxCuotasTipoColegioRepository;

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

    @Test
    public void testIngresarEstudiante() {
        IngresarEstudianteRequest request = new IngresarEstudianteRequest();

        request.setEstudiante(estudiante);
        System.out.println(estudiante);
        request.setIdTipoColegio(1);
        request.setIdTipoPagoArancel(2);

        // Crear objetos TipoColegio y TipoPagoArancel simulados
        TipoColegio tipoColegio = new TipoColegio();
        tipoColegio.setId(1);

        TipoPagoArancel tipoPagoArancel = new TipoPagoArancel();
        tipoPagoArancel.setId(2);

        // Simular comportamiento de los repositorios
        when(tipoColegioRepository.findById(1)).thenReturn(Optional.of(tipoColegio));
        when(tipoPagoArancelRepository.findById(2)).thenReturn(Optional.of(tipoPagoArancel));
        when(estudianteRepository.save(request.getEstudiante())).thenReturn(estudiante);

        // Ejecutar el método a probar
        Estudiante resultado = estudianteService.ingresarEstudiante(request);

        // Verificar que se guardó el estudiante correctamente
        verify(estudianteRepository, times(1)).save(estudiante);

        assertEquals(estudiante, resultado);
    }

    @Test
    public void testIngresarEstudianteTipoColegioNoExiste() {
        IngresarEstudianteRequest request = new IngresarEstudianteRequest();
        request.setEstudiante(estudiante);
        request.setIdTipoColegio(4);
        request.setIdTipoPagoArancel(2);

        //Simular
        when(tipoColegioRepository.findById(4)).thenReturn(Optional.empty());

        assertThrows(RegistroNoExisteException.class, () -> estudianteService.ingresarEstudiante(request));

        verify(estudianteRepository, never()).save(estudiante);
    }

    @Test
    public void testIngresarEstudianteTipoPagoArancelNoExiste() {
        IngresarEstudianteRequest request = new IngresarEstudianteRequest();
        request.setEstudiante(estudiante);
        request.setIdTipoColegio(1);
        request.setIdTipoPagoArancel(4);

        // Simular
        TipoColegio tipoColegio = new TipoColegio();
        tipoColegio.setId(1);

        when(tipoColegioRepository.findById(1)).thenReturn(Optional.of(tipoColegio));
        when(tipoPagoArancelRepository.findById(4)).thenReturn(Optional.empty());

        // Test
        assertThrows(RegistroNoExisteException.class, () -> estudianteService.ingresarEstudiante(request));

        verify(estudianteRepository, never()).save(estudiante);
    }

    @Test
    public void testObtenerMaxCuotas() {
        String rut = estudiante.getRut();

        // Simular
        TipoColegio tipoColegio = new TipoColegio();
        tipoColegio.setId(1);
        estudiante.setTipoColegio(tipoColegio);

        when(estudianteRepository.findByRut(rut)).thenReturn(Optional.of(estudiante));
        when(maxCuotasTipoColegioRepository.findMaxCuotasByTipoColegio(estudiante.getTipoColegio().getId())).thenReturn(7);


        // Prueba
        Integer resultado = estudianteService.obtenerMaxCuotas(estudiante.getRut());

        assertEquals(7, resultado);
    }

    @Test
    public void testObtenerMaxCuotasEstudianteNoExiste() {
        String rut = "12.345.678-9";

        // Simular
        TipoColegio tipoColegio = new TipoColegio();
        tipoColegio.setId(1);
        estudiante.setTipoColegio(tipoColegio);

        when(estudianteRepository.findByRut(rut)).thenReturn(Optional.empty());

        // Test
        assertThrows(RegistroNoExisteException.class, () -> estudianteService.obtenerMaxCuotas(rut));

        verify(maxCuotasTipoColegioRepository, never()).findMaxCuotasByTipoColegio(estudiante.getTipoColegio().getId());
    }
}