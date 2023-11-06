package com.mingeso.topeducation_ms2.dtos;

import com.mingeso.topeducation_ms2.entities.Razon;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class EstudianteResponse extends Response {
    EstudianteDTO estudiante;
}
