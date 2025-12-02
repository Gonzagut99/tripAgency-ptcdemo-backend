package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.services.LiquidationTotalsService;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Incidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.IncidencyStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IIncidencyJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateIncidencyDto;

@Service
public class UpdateIncidencyCommandHandler {
    
    private final IIncidencyJpaRepository incidencyRepository;
    private final LiquidationTotalsService liquidationTotalsService;
    
    public UpdateIncidencyCommandHandler(
            IIncidencyJpaRepository incidencyRepository,
            LiquidationTotalsService liquidationTotalsService) {
        this.incidencyRepository = incidencyRepository;
        this.liquidationTotalsService = liquidationTotalsService;
    }
    
    @Transactional
    public Incidency execute(UpdateIncidencyCommand command) {
        Incidency incidency = incidencyRepository.findById(command.incidencyId())
                .orElseThrow(() -> new IllegalArgumentException("La incidencia no fue encontrada con id: " + command.incidencyId()));
        
        // Verificar que la incidencia pertenece a la liquidación correcta
        if (!incidency.getLiquidationId().equals(command.liquidationId())) {
            throw new IllegalArgumentException("La incidencia no pertenece a la liquidación especificada");
        }
        
        UpdateIncidencyDto dto = command.incidencyDto();
        
        incidency.setReason(dto.getReason());
        incidency.setAmount(dto.getAmount());
        incidency.setIncidencyDate(dto.getIncidencyDate());
        incidency.setIncidencyStatus(IncidencyStatus.valueOf(dto.getIncidencyStatus()));
        
        Incidency saved = incidencyRepository.save(incidency);
        
        // Recalcular totales de la liquidación
        liquidationTotalsService.recalculateAndSaveTotals(command.liquidationId());
        
        return saved;
    }
}
