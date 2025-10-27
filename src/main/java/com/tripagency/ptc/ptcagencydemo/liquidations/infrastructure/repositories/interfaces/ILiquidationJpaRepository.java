package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Liquidation;

@Repository
public interface ILiquidationJpaRepository extends JpaRepository<Liquidation, Long> {
    //Page<Liquidation> findAll(Pageable pageable);
}
