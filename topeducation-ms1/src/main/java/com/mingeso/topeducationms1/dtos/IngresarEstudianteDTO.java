package com.mingeso.topeducationms1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class IngresarEstudianteDTO {
    String rut;
    String nombre1;
    String nombre2;
    String apellido1;
    String apellido2;
    LocalDate fechaNacimiento;
    Integer anioEgreso;
    String nombreColegio;
    int idTipoColegio;
    int idTipoPagoArancel;
}
