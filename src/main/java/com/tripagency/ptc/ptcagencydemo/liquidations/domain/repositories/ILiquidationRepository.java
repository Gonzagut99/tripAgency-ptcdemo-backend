package com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;

public interface ILiquidationRepository {

    DLiquidation save(DLiquidation liquidation);

    Optional<DLiquidation> findById(Long id);

    Page<DLiquidation> findAll(Pageable pageable);

    Page<DLiquidation> findByCustomerId(Long customerId, Pageable pageable);

    Page<DLiquidation> findByStatus(DLiquidationStatus status, Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);
}
