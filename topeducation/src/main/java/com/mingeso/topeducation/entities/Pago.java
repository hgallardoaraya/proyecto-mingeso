package com.mingeso.topeducation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pago")
public class Pago {
    @Id
    Integer id;
    @Column(name = "total")
    Integer total;
    @Column(name = "fecha")
    Date fecha;
    @ManyToOne
    @JoinColumn(name="id_estudiante", nullable=false)
    @JsonIgnore
    @JsonBackReference
    Estudiante estudiante;
}
