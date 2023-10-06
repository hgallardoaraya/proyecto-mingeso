package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.DescuentoPuntajePrueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DescuentoPuntajePruebaRepository extends JpaRepository<DescuentoPuntajePrueba, Integer> {
    @Query("SELECT d.porcentajeDescuento " +
            "FROM DescuentoPuntajePrueba d " +
            "WHERE :puntaje " +
            "BETWEEN d.puntajeInferior " +
            "AND d.puntajeSuperior")
    Integer findDescuentoByPuntaje(@Param("puntaje") Integer puntaje);
}
