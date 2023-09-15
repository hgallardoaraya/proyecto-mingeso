package com.mingeso.topeducation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
