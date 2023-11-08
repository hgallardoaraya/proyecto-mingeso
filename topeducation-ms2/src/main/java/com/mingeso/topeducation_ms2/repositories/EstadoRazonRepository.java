package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.EstadoRazon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRazonRepository extends JpaRepository<EstadoRazon, Integer> {
    @Query("SELECT e " +
            "FROM EstadoRazon e " +
            "WHERE e.estado = :estado")
    EstadoRazon findEstadoRazonByEstado(@Param("estado") String estado);
}
