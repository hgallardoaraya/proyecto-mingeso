package com.mingeso.topeducation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    Integer anioEgreso;
    @Column(name = "nombre_colegio")
    String nombreColegio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_colegio", referencedColumnName = "id")
    @JsonManagedReference
    TipoColegio tipoColegio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_pago_arancel", referencedColumnName = "id")
    @JsonManagedReference
    @JsonIgnore
    TipoPagoArancel tipoPagoArancel;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_interes_meses_atraso", referencedColumnName = "id")
//    @JsonManagedReference
    @JsonIgnore
    InteresMesesAtraso interesMesesAtraso;
    @OneToMany(mappedBy="estudiante")
//    @JsonManagedReference
    @JsonIgnore
    Set<Razon> razones;
    @OneToMany(mappedBy="estudiante")
//    @JsonManagedReference
    @JsonIgnore
    List<Examen> examenes;
}
