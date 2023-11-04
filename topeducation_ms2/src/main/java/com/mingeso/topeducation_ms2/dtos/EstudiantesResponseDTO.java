package com.mingeso.topeducation_ms2.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstudiantesResponseDTO {
    Integer status;
    String message;
    String pathRedirect;
    Map<String, List<EstudianteDTO>> data;

    public EstudiantesResponseDTO(Integer status, String message, Map<String, List<EstudianteDTO>> data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public EstudiantesResponseDTO(Integer status, String message){
        this.status = status;
        this.message = message;
    }

}
