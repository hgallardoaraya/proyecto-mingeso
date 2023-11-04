package com.mingeso.topeducation_ms2.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class EstudianteResponseDTO extends Response {
    Map<String, EstudianteDTO> data;
}
