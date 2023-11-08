package com.mingeso.topeducation_ms2.dtos.pagos;

import com.mingeso.topeducation_ms2.dtos.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PagosResponse extends Response {
    List<PagoDTO> pagos;

    public PagosResponse(Integer status, String message, List<PagoDTO> pagos){
        super(status, message);
        this.pagos = pagos;
    }
}