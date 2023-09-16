package com.mingeso.topeducation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipo_pago_arancel")
public class TipoPagoArancel {
    @Id
    Integer id;
    @Column(name = "tipo")
    String tipo;
    @OneToOne(mappedBy = "tipoPagoArancel")
    Estudiante estudiante;
}
