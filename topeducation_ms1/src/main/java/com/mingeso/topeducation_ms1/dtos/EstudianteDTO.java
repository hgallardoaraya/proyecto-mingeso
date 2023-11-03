package com.mingeso.topeducation_ms1.dtos;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;


@Builder
@Getter
public class EstudianteDTO {
    Integer id;
    String rut;
    String nombre1;
    String nombre2;
    String apellido1;
    String apellido2;
    LocalDate fechaNacimiento;
    Integer anioEgreso;
    String nombreColegio;
    String tipoColegio;
    String tipoPagoArancel;
}
