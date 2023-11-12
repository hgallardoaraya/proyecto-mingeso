package com.mingeso.topeducationms1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tipo_pago_arancel")
public class TipoPagoArancel {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "tipo")
    String tipo;
}