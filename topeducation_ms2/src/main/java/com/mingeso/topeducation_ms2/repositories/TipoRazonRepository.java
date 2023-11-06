package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.TipoRazon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRazonRepository extends JpaRepository<TipoRazon, Integer> {
    @Query("SELECT t " +
            "FROM TipoRazon t " +
            "WHERE t.tipo = :tipo")
    TipoRazon findTipoRazonByTipo(@Param("tipo") String tipo);
}
