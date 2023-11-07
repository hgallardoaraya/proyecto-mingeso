package com.mingeso.topeducation_ms2.dtos.pagos;

import com.mingeso.topeducation_ms2.dtos.Response;
import com.mingeso.topeducation_ms2.entities.Pago;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PagoResponse extends Response {
    PagoDTO pago;

    public PagoResponse(Integer status, String message, PagoDTO pago){
        super(status, message);
        this.pago = pago;
    }
}
