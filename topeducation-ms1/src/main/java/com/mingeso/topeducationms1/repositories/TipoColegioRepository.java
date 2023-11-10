package com.mingeso.topeducationms1.repositories;

import com.mingeso.topeducationms1.entities.TipoColegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoColegioRepository extends JpaRepository<TipoColegio, Integer> {
}
