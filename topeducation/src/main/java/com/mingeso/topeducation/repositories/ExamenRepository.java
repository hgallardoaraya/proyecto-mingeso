package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Integer> {
    @Query("SELECT e from Examen e where e.revision = false")
    List<Examen> findAllSinRevision();
}
