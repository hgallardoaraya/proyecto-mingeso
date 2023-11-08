package com.mingeso.topeducation_ms3.repositories;

import com.mingeso.topeducation_ms3.entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Integer> {
    @Query("SELECT e " +
            "FROM Examen e " +
            "WHERE e.revision = 0 ")
    List<Examen> findAllSinRevision();
}
