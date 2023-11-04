package com.mingeso.topeducation_ms2.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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