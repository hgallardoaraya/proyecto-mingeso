package com.mingeso.topeducation.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_razon")
public class TipoRazon {
    @Id
    Integer id;
    @Column(name = "tipo")
    String tipo;
    @OneToOne(mappedBy = "tipo")
    Razon razon;
}
