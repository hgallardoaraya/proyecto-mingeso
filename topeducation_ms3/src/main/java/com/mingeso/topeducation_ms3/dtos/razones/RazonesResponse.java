package com.mingeso.topeducation_ms3.dtos.razones;

import com.mingeso.topeducation_ms3.dtos.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RazonesResponse extends Response {
    List<RazonDTO> razones;

    public RazonesResponse(Integer status, String message, List<RazonDTO> razones){
        super(status, message);
        this.razones = razones;
    }
}