package com.mingeso.topeducation_ms2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
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
    @JsonManagedReference(value="razon_tipo")
    TipoRazon tipo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado_razon", referencedColumnName = "id")
    @JsonManagedReference(value="razon_estado")
    EstadoRazon estado;
    @ManyToMany(mappedBy = "razones")
    @JsonBackReference(value="pagos_razones")
    @JsonIgnore
    List<Pago> pagos;

    public Razon(Integer numero, Integer monto, LocalDate fechaInicio, LocalDate fechaFin, TipoRazon tipo, EstadoRazon estado, Integer idEstudiante){
        this.numero = numero;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.estado = estado;
        this.idEstudiante = idEstudiante;
    }
}
