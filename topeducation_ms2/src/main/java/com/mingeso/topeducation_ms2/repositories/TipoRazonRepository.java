package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.TipoRazon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRazonRepository extends JpaRepository<TipoRazon, Integer> {
}
