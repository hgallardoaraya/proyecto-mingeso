package com.mingeso.topeducation_ms2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "max_cuotas_tipo_colegio")
public class MaxCuotasTipoColegio {
    @Id
    Integer id;
    @Column(name = "max_cuotas")
    Integer maxCuotas;
    @Column(name = "id_tipo_colegio")
    Integer idTipoColegio;
}
