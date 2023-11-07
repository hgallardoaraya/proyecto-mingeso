package com.mingeso.topeducation_ms3.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "examen")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "fecha")
    LocalDate fecha;
    @Column(name = "puntaje")
    Integer puntaje;
    @Column(name = "revision")
    int revision;
    @Column(name = "id_estudiante")
    Integer idEstudiante;


    public Examen(LocalDate fecha, Integer puntaje, Integer idEstudiante, int revisado){
        this.fecha = fecha;
        this.puntaje = puntaje;
        this.idEstudiante = idEstudiante;
        this.revision = revisado;
    }
}

