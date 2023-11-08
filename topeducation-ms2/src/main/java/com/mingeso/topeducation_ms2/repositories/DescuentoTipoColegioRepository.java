package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.DescuentoTipoColegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoTipoColegioRepository extends JpaRepository<DescuentoTipoColegio, Integer> {
    @Query("SELECT d.porcentajeDescuento " +
            "FROM DescuentoTipoColegio d " +
            "WHERE d.idTipoColegio = :id_tipo_colegio")
    Integer findDescuentoByIdTipoColegio(@Param("id_tipo_colegio") Integer idTipoColegio);
}
