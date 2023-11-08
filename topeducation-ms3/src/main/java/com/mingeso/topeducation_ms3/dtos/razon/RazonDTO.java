package com.mingeso.topeducation_ms3.dtos.razon;

import com.mingeso.topeducation_ms3.dtos.estado_razon.EstadoRazonDTO;
import com.mingeso.topeducation_ms3.dtos.tipo_razon.TipoRazonDTO;
import lombok.*;

import java.time.LocalDate;

@Data
public class RazonDTO {
    Integer id;
    Integer numero;
    Integer monto;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    Integer idEstudiante;
    TipoRazonDTO tipo;
    EstadoRazonDTO estado;
}