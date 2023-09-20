package com.mingeso.topeducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_colegio")
public class TipoColegio {
    @Id
    Integer id;
    @Column(name = "tipo")
    String tipo;
    @OneToOne(mappedBy = "tipoColegio")
    Estudiante estudiante;
    @OneToOne(mappedBy = "tipoColegio")
    MaxCuotasTipoColegio maxCuotasTipoColegio;
}
