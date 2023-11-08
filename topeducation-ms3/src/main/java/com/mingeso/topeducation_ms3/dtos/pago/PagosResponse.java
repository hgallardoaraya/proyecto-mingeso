package com.mingeso.topeducation_ms3.dtos.pago;

import com.mingeso.topeducation_ms3.dtos.Response;
import com.mingeso.topeducation_ms3.dtos.razon.RazonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class PagosResponse extends Response {
    List<PagoDTO> pagos;

    public PagosResponse(Integer status, String message, List<PagoDTO> pagos){
        super(status, message);
        this.pagos = pagos;
    }
}