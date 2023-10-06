package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.Razon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface RazonRepository extends JpaRepository<Razon, Integer> {
    @Query("SELECT r from Razon r where r.estudiante.rut = :rut")
    ArrayList<Razon> findAllByRut(@Param("rut") String rut);

    @Query("SELECT r from Razon r where r.estudiante.rut = :rut and (r.estado.id = 1 or r.estado.id = 2)")
    ArrayList<Razon> findAllPendientesByRut(@Param("rut") String rut);

    @Query("SELECT r FROM Razon r WHERE r.estado.id = 1 OR r.estado.id = 2")
    List<Razon> findAllPendientes();

    @Query("SELECT r FROM Razon r WHERE (SELECT MIN(r.fechaFin) FROM Razon r WHERE r.fechaFin > :fecha_actual) = r.fechaFin")
    Razon findCuotaProceso(@Param("fecha_actual") LocalDate fechaActual);
}
