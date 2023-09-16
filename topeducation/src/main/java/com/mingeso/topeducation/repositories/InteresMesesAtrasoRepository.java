package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.InteresMesesAtraso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresMesesAtrasoRepository extends JpaRepository<InteresMesesAtraso, Integer> {
}
