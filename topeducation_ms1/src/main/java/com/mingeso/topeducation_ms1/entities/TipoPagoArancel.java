package com.mingeso.topeducation_ms1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference(value = "estudiante_tipoPagoArancel")
    Estudiante estudiante;
}