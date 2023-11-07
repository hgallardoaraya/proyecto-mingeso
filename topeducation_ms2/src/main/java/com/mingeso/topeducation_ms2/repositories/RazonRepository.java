package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.Razon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RazonRepository extends JpaRepository<Razon, Integer> {
    @Query("SELECT COUNT(r) " +
            "FROM Razon r " +
            "WHERE r.idEstudiante = :id_estudiante " +
            "AND r.fechaInicio BETWEEN :fecha_inicio_proceso_anual AND :fecha_fin_proceso_anual")
    Long findCantidadRazonesProcesoByIdEstudiante(
            @Param("id_estudiante") Integer idEstudiante,
            @Param("fecha_inicio_proceso_anual") LocalDate fechaInicioProcesoAnual,
            @Param("fecha_fin_proceso_anual") LocalDate fechaFinProcesoAnual);

    @Query("SELECT r " +
            "FROM Razon r " +
            "WHERE r.idEstudiante = :id_estudiante")
    List<Razon> findAllByIdEstudiante(@Param("id_estudiante") Integer idEstudiante);
}
