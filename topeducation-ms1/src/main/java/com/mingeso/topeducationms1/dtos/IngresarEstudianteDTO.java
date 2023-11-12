package com.mingeso.topeducationms1.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
