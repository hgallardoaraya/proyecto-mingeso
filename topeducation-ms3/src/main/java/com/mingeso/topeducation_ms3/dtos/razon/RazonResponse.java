package com.mingeso.topeducation_ms3.dtos.razon;

import com.mingeso.topeducation_ms3.dtos.Response;
import lombok.Getter;

@Getter
public class RazonResponse extends Response {
    RazonDTO razon;

    public RazonResponse(Integer status, String message, RazonDTO razon){
        super(status, message);
        this.razon = razon;
    }
}
