package com.mingeso.topeducation_ms2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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
    LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    LocalDate fechaFin;
    @Column(name = "id_estudiante")
    Integer idEstudiante;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_razon", referencedColumnName = "id")
    @JsonManagedReference
    TipoRazon tipo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado_razon", referencedColumnName = "id")
    @JsonManagedReference
    EstadoRazon estado;
    @ManyToMany(mappedBy = "razones")
    @JsonBackReference
    Set<Pago> pagos;
}