package com.mingeso.topeducation_ms1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstudiantesResponse extends Response {
    List<EstudianteDTO> estudiantes;

    public EstudiantesResponse(Integer status, String message, List<EstudianteDTO> estudiantes){
        super(status, message);
        this.estudiantes = estudiantes;
    }
}