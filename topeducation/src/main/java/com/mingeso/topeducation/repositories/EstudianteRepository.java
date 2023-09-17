package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    @Query("SELECT e FROM Estudiante e WHERE e.rut = :rut")
    Estudiante findByRut(@Param("rut") String rut);
}
