package com.mingeso.topeducationms1.repositories;

import com.mingeso.topeducationms1.entities.TipoPagoArancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoArancelRepository extends JpaRepository<TipoPagoArancel, Integer> {
}
