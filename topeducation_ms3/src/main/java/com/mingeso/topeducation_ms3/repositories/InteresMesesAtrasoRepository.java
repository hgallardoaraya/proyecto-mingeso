package com.mingeso.topeducation_ms3.repositories;

import com.mingeso.topeducation_ms3.entities.InteresMesesAtraso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresMesesAtrasoRepository extends JpaRepository<InteresMesesAtraso, Integer> {
}
