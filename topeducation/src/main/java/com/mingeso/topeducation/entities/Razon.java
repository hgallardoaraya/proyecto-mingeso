package com.mingeso.topeducation.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "razon")
@NoArgsConstructor
@Data
public class Razon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "numero_razon")
    Integer numero;
    @Column(name = "monto")
    Integer monto;
    @Column(name = "fecha_inicio")
    Date fechaInicio;
    @Column(name = "fecha_fin")
    Date fechaFin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_razon", referencedColumnName = "id")
    TipoRazon tipo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado_razon", referencedColumnName = "id")
    EstadoRazon estado;
    @ManyToOne
    @JoinColumn(name="id_estudiante", nullable=false)
    Estudiante estudiante;
}
