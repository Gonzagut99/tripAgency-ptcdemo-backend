package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Liquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.LiquidationStatus;

@Repository
public interface ILiquidationJpaRepository extends JpaRepository<Liquidation, Long> {
    
    Page<Liquidation> findByCustomerId(Long customerId, Pageable pageable);
    
    Page<Liquidation> findByStatus(LiquidationStatus status, Pageable pageable);
}
