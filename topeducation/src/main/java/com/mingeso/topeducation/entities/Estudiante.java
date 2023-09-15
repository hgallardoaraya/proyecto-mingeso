package com.mingeso.topeducation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Year;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
public class Estudiante {
    @Id
    Integer id;
    @Column(name = "rut")
    String rut;
    @Column(name = "nombre1")
    String nombre1;
    @Column(name = "nombre2")
    String nombre2;
    @Column(name = "apellido1")
    String apellido1;
    @Column(name = "apellido2")
    String apellido2;
    @Column(name = "fecha_nacimiento")
    Date fechaNacimiento;
    @Column(name = "anio_egreso")
    Year anioEgreso;
    @Column(name = "nombre_colegio")
    String nombreColegio;
    String tipoColegio;
    String tipoPagoArancel;
    Integer interes;
    //Lista razones
}
