package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Integer> {
    @Query("SELECT e " +
            "FROM Examen e " +
            "WHERE e.revision = false " +
            "AND e.fecha < CURDATE()")
    List<Examen> findAllSinRevision();
}
