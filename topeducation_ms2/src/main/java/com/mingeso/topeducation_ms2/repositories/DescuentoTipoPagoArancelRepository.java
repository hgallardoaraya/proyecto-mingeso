package com.mingeso.topeducation_ms2.repositories;

import com.mingeso.topeducation_ms2.entities.DescuentoTipoPagoArancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoTipoPagoArancelRepository extends JpaRepository<DescuentoTipoPagoArancel, Integer> {
    @Query("SELECT d.porcentajeDescuento " +
            "FROM DescuentoTipoPagoArancel d " +
            "WHERE d.idTipoPagoArancel = :id_tipo_pago_arancel")
    Integer findDescuentoByIdTipoPagoArancel(@Param("id_tipo_pago_arancel") Integer idTipoPagoArancel);
}
