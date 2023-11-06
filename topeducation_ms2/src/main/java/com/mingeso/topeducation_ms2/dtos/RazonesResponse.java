package com.mingeso.topeducation_ms2.dtos;

import com.mingeso.topeducation_ms2.entities.Razon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RazonesResponse extends Response {
    List<Razon> razones;

    public RazonesResponse(Integer status, String message, List<Razon> razones){
        super(status, message);
        this.razones = razones;
    }
}