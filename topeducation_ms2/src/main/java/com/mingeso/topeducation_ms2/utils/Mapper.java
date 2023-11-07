package com.mingeso.topeducation_ms2.utils;

import com.mingeso.topeducation_ms2.dtos.pagos.PagoDTO;
import com.mingeso.topeducation_ms2.entities.Pago;
import com.mingeso.topeducation_ms2.entities.Razon;

public class Mapper {

    public static PagoDTO pagoToPagoDTO(Pago pago){
        return PagoDTO.builder()
                .id(pago.getId())
                .total(pago.getTotal())
                .fecha(pago.getFecha())
                .idsRazones(
                        pago.getRazones().stream().map(Razon::getId).toList()
                )
                .build();
    }
}
