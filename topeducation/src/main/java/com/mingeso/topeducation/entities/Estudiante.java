package com.mingeso.topeducation.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;

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
    @JsonFormat(pattern="yyyy-MM-dd")
    Date fechaNacimiento;
    @Column(name = "anio_egreso")
    Year anioEgreso;
    @Column(name = "nombre_colegio")
    String nombreColegio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_colegio", referencedColumnName = "id")
    TipoColegio tipoColegio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_pago_arancel", referencedColumnName = "id")
    TipoPagoArancel tipoPagoArancel;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_interes_meses_atraso", referencedColumnName = "id")
    InteresMesesAtraso interesMesesAtraso;
}
