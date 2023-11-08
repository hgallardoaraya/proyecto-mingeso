package com.mingeso.topeducation_ms2.dtos.estudiantes;

import com.mingeso.topeducation_ms2.dtos.tipo_colegio.TipoColegioDTO;
import com.mingeso.topeducation_ms2.dtos.tipo_pago_arancel.TipoPagoArancelDTO;
import lombok.Data;
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
    Integer cuotasPactadas;
    String nombreColegio;
    TipoColegioDTO tipoColegio;
    TipoPagoArancelDTO tipoPagoArancel;
}