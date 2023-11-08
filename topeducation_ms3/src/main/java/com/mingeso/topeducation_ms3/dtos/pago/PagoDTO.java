package com.mingeso.topeducation_ms3.dtos.pago;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class PagoDTO {
    Integer id;
    Integer total;
    Integer idEstudiante;
    LocalDate fecha;
    List<Integer> idsRazones;
}