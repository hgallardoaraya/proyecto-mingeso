package com.mingeso.topeducation_ms3.repositories;

import com.mingeso.topeducation_ms3.entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Integer> {
}
