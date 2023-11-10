package com.mingeso.topeducationms1.dtos;

import com.mingeso.topeducationms1.entities.TipoColegio;
import com.mingeso.topeducationms1.entities.TipoPagoArancel;
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
    Integer cuotasPactadas;
    String nombreColegio;
    TipoColegio tipoColegio;
    TipoPagoArancel tipoPagoArancel;
}
