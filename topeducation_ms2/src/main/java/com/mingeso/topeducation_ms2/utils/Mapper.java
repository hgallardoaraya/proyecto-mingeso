package com.mingeso.topeducation_ms2.utils;

import com.mingeso.topeducation_ms2.dtos.pagos.PagoDTO;
import com.mingeso.topeducation_ms2.entities.Pago;
import com.mingeso.topeducation_ms2.entities.Razon;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static PagoDTO pagoToPagoDTO(Pago pago){
        return PagoDTO.builder()
                .id(pago.getId())
                .total(pago.getTotal())
                .idEstudiante(pago.getIdEstudiante())
                .fecha(pago.getFecha())

                .idsRazones(
                        pago.getRazones().stream().map(Razon::getId).toList()
                )
                .build();
    }

    public static List<PagoDTO> pagosToPagosDTO(List<Pago> pagos) {
        List<PagoDTO> pagosDTO = new ArrayList<>();
        for(Pago pago : pagos){
            PagoDTO pagoDTO = pagoToPagoDTO(pago);
            pagosDTO.add(pagoDTO);
        }
        return pagosDTO;
    }
}
