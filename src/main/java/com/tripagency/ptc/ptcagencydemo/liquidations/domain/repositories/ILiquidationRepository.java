package com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ILiquidationRepository {
    
    DLiquidation save(DLiquidation liquidation);
    
    Optional<DLiquidation> findById(Long id);
    
    Page<DLiquidation> findAll(Pageable pageable);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
