package com.mingeso.topeducation_ms3.dtos.reporte;

import com.mingeso.topeducation_ms3.dtos.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ReporteResponse extends Response {
    List<EntradaReporteResumen> reporte;

    public ReporteResponse(Integer status, String message, List<EntradaReporteResumen> reporte){
        super(status, message);
        this.reporte = reporte;
    }
}
