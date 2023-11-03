package com.mingeso.topeducation_ms1.repositories;

import com.mingeso.topeducation_ms1.entities.TipoPagoArancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoArancelRepository extends JpaRepository<TipoPagoArancel, Integer> {
}
