package com.mingeso.topeducation_ms1.repositories;

import com.mingeso.topeducation_ms1.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    @Query("SELECT e " +
            "FROM Estudiante e " +
            "WHERE e.rut = :rut")
    Optional<Estudiante> findByRut(@Param("rut") String rut);
}
