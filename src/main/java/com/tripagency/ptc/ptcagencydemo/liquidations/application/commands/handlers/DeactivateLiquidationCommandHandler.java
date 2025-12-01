package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateLiquidationCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Liquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.ILiquidationJpaRepository;

import jakarta.transaction.Transactional;

@Service
public class DeactivateLiquidationCommandHandler {
    private final ILiquidationJpaRepository liquidationJpaRepository;

    public DeactivateLiquidationCommandHandler(ILiquidationJpaRepository liquidationJpaRepository) {
        this.liquidationJpaRepository = liquidationJpaRepository;
    }

    @Transactional
    public Liquidation execute(DeactivateLiquidationCommand command) {
        try {
            Liquidation existingLiquidation = liquidationJpaRepository.findById(command.liquidationId())
                .orElseThrow(() -> new IllegalArgumentException("No existe una liquidación con el ID: " + command.liquidationId()));

            existingLiquidation.setIsActive(false);
            return liquidationJpaRepository.save(existingLiquidation);
        } catch (Exception e) {
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
