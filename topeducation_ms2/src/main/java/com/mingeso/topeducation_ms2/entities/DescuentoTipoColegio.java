package com.mingeso.topeducation_ms2.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "descuento_tipo_colegio")
public class DescuentoTipoColegio {
    @Id
    Integer id;
    @Column(name = "id_tipo_colegio")
    Integer idTipoColegio;
    @Column(name = "porcentaje_descuento")
    Integer porcentajeDescuento;
}
