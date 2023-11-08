package com.mingeso.topeducation_ms2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "total")
    Integer total;
    @Column(name = "fecha")
    LocalDate fecha;
    @Column(name = "id_estudiante")
    Integer idEstudiante;
    @ManyToMany
    @JoinTable(
            name = "pago_razon",
            joinColumns = @JoinColumn(name = "id_pago"),
            inverseJoinColumns = @JoinColumn(name = "id_razon"))
    @JsonManagedReference(value="pagos_razones")
    List<Razon> razones;

}