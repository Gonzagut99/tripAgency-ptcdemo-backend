package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddTourServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DTour;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DTourService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddTourServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.TourDto;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;

@Service
public class AddTourServiceCommandHandler {
    
    private final ILiquidationRepository liquidationRepository;
    
    public AddTourServiceCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }
    
    @Transactional
    public DLiquidation execute(AddTourServiceCommand command) {
        DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
                .orElseThrow(() -> new IllegalArgumentException("La liquidación no fue encontrada con id: " + command.liquidationId()));
        
        AddTourServiceDto dto = command.tourServiceDto();
        
        DTourService tourService = new DTourService(
                dto.getTariffRate(),
                dto.getIsTaxed(),
                DCurrency.valueOf(dto.getCurrency()),
                command.liquidationId()
        );
        
        for (TourDto tourDto : dto.getTours()) {
            DTour tour = new DTour(
                    tourDto.getStartDate(),
                    tourDto.getEndDate(),
                    tourDto.getTitle(),
                    tourDto.getPrice(),
                    tourDto.getPlace(),
                    DCurrency.valueOf(tourDto.getCurrency()),
                    null
            );
            tourService.addTour(tour);
        }
        
        liquidation.addTourService(tourService);
        
        return liquidationRepository.save(liquidation);
    }
}
