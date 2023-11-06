package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.MaxCuotasTipoColegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaxCuotasTipoColegioRepository extends JpaRepository<MaxCuotasTipoColegio, Integer> {
    @Query("SELECT m.maxCuotas " +
            "FROM MaxCuotasTipoColegio m WHERE m.idTipoColegio = :id_tipo_colegio")
    Integer findMaxCuotasByTipoColegio(@Param("id_tipo_colegio") Integer idTipoColegio);
}
