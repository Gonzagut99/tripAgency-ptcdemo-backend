package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Incidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IIncidencyJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.ILiquidationJpaRepository;

import jakarta.transaction.Transactional;

@Service
public class DeactivateIncidencyCommandHandler {
    private final IIncidencyJpaRepository incidencyJpaRepository;
    private final ILiquidationJpaRepository liquidationJpaRepository;

    public DeactivateIncidencyCommandHandler(IIncidencyJpaRepository incidencyJpaRepository,
                                             ILiquidationJpaRepository liquidationJpaRepository) {
        this.incidencyJpaRepository = incidencyJpaRepository;
        this.liquidationJpaRepository = liquidationJpaRepository;
    }

    @Transactional
    public Incidency execute(DeactivateIncidencyCommand command) {
        try {
            // Verify liquidation exists
            if (!liquidationJpaRepository.existsById(command.liquidationId())) {
                throw new IllegalArgumentException("No existe una liquidación con el ID: " + command.liquidationId());
            }

            Incidency existingIncidency = incidencyJpaRepository.findById(command.incidencyId())
                .orElseThrow(() -> new IllegalArgumentException("No existe una incidencia con el ID: " + command.incidencyId()));

            existingIncidency.setIsActive(false);
            return incidencyJpaRepository.save(existingIncidency);
        } catch (Exception e) {
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
