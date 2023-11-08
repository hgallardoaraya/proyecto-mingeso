package com.mingeso.topeducation_ms3.dtos.estudiante;

import com.mingeso.topeducation_ms3.dtos.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstudiantesResponse extends Response {
    List<EstudianteDTO> estudiantes;
}
