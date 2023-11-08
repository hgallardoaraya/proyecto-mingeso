package com.mingeso.topeducation_ms1.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstudianteResponse extends Response {
    EstudianteDTO estudiante;

    public EstudianteResponse(Integer status, String message, EstudianteDTO estudiante){
        super(status, message);
        this.estudiante = estudiante;
    }
}