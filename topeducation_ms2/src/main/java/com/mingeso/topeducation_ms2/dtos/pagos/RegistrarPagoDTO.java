package com.mingeso.topeducation_ms2.dtos.pagos;


import lombok.Data;

@Data
public class RegistrarPagoDTO {
    String rut;
    Integer[] idsRazones;
}
