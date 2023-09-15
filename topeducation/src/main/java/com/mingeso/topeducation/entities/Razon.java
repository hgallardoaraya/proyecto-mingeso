package com.mingeso.topeducation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "razon")
@NoArgsConstructor
@Data
public class Razon {
    @Id
    Integer id;
    String tipo;
    @Column(name = "numero_razon")
    Integer numero;
    @Column(name = "monto")
    Integer monto;
    String estado;
    @Column(name = "fecha_inicio")
    Date fechaInicio;
    @Column(name = "fecha_fin")
    Date fechaFin;
}
