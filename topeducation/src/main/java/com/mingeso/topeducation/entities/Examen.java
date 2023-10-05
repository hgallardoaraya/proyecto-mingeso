package com.mingeso.topeducation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    Date  fecha;
    @Column(name = "puntaje")
    Double puntaje;
    @ManyToOne
    @JoinColumn(name="id_estudiante", nullable=false)
    @JsonBackReference
    Estudiante estudiante;

    public Examen(Date fecha, Double puntaje, Estudiante estudiante){
        this.fecha = fecha;
        this.puntaje = puntaje;
        this.estudiante = estudiante;
    }
}
