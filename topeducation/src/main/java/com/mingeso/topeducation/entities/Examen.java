package com.mingeso.topeducation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "examen")
public class Examen {
    @Id
    Integer id;
    @Column(name = "fecha")
    Date fecha;
    @Column(name = "puntaje")
    Integer puntaje;
    @ManyToOne
    @JoinColumn(name="id_estudiante", nullable=false)
    @JsonBackReference
    Estudiante estudiante;
}
