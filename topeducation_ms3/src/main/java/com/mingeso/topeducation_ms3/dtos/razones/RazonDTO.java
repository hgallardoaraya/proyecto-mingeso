package com.mingeso.topeducation_ms3.dtos.razones;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mingeso.topeducation_ms3.dtos.EstadoRazonDTO.EstadoRazonDTO;
import com.mingeso.topeducation_ms3.dtos.tipo_razon.TipoRazonDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class RazonDTO {
    Integer id;
    Integer numero;
    Integer monto;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    Integer idEstudiante;
    TipoRazonDTO tipo;
    EstadoRazonDTO estado;
}