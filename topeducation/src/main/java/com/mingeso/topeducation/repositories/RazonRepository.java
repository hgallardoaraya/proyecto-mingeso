package com.mingeso.topeducation.repositories;

import com.mingeso.topeducation.entities.Razon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RazonRepository extends JpaRepository<Razon, Integer> {
    @Query("SELECT r from Razon r where r.estudiante.rut = :rut")
    ArrayList<Razon> findAllByRut(@Param("rut") String rut);
}
