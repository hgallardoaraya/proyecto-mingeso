package com.mingeso.topeducation_ms2.dtos.razones;

import com.mingeso.topeducation_ms2.dtos.Response;
import com.mingeso.topeducation_ms2.entities.Razon;

public class RazonResponse extends Response {
    Razon razon;

    public RazonResponse(Integer status, String message, Razon razon){
        super(status, message);
        this.razon = razon;
    }
}
