package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.TotalRazon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalRazonRepository extends JpaRepository<TotalRazon, Integer> {
    @Query("SELECT t.total " +
            "FROM TotalRazon t " +
            "WHERE t.tipo.tipo = :tipo_razon ")
    Integer findTotalByTipoRazon(@Param("tipo_razon") String tipoRazon);
}
