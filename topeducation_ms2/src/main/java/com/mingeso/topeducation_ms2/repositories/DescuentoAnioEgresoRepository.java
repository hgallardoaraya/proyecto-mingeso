package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.DescuentoAnioEgreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoAnioEgresoRepository extends JpaRepository<DescuentoAnioEgreso, Integer> {
}
