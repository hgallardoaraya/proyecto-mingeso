package com.mingeso.topeducation_ms2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "descuento_tipo_pago_arancel")
public class DescuentoTipoPagoArancel {
    @Id
    Integer id;
    @Column(name = "id_tipo_pago_arancel")
    Integer idTipoPagoArancel;
    @Column(name = "porcentaje_descuento")
    Integer porcentajeDescuento;
}
