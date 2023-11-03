package com.mingeso.topeducation_ms1.repositories;

import com.mingeso.topeducation_ms1.entities.TipoColegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoColegioRepository extends JpaRepository<TipoColegio, Integer> {
}
