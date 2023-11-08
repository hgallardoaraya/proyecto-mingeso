package com.mingeso.topeducation_ms2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "total_razon")
public class TotalRazon {
    @Id
    Integer id;
    @Column(name = "total")
    Integer total;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipo_razon", referencedColumnName = "id")
    @JsonManagedReference(value = "total_tipo")
    @JsonIgnore
    TipoRazon tipo;
}
