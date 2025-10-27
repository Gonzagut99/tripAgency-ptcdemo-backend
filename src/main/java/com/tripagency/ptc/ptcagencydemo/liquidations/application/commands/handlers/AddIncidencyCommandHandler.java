package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DIncidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddIncidencyDto;

@Service
public class AddIncidencyCommandHandler {
    
    private final ILiquidationRepository liquidationRepository;
    
    public AddIncidencyCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }
    
    @Transactional
    public DLiquidation execute(AddIncidencyCommand command) {
        DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
                .orElseThrow(() -> new IllegalArgumentException("Liquidation not found with id: " + command.liquidationId()));
        
        AddIncidencyDto dto = command.incidencyDto();
        
        DIncidency incidency = new DIncidency(
                dto.getReason(),
                Optional.ofNullable(dto.getAmount()),
                dto.getIncidencyDate(),
                command.liquidationId()
        );
        
        liquidation.addIncidency(incidency);
        
        return liquidationRepository.save(liquidation);
    }
}
