package com.mingeso.topeducation_ms3.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interes_meses_atraso")
public class InteresMesesAtraso {
    @Id
    Integer id;
    @Column(name = "meses_atraso")
    Integer mesesAtraso;
    @Column(name = "porcentaje_interes")
    Integer porcentajeInteres;
}
