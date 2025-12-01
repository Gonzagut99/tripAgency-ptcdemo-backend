package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateTourCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Tour;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.ServiceStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.ITourJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateTourDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Currency;

@Service
public class UpdateTourCommandHandler {
    
    private final ITourJpaRepository tourRepository;
    
    public UpdateTourCommandHandler(ITourJpaRepository tourRepository) {
        this.tourRepository = tourRepository;
    }
    
    @Transactional
    public Tour execute(UpdateTourCommand command) {
        Tour tour = tourRepository.findById(command.tourId())
                .orElseThrow(() -> new IllegalArgumentException("El tour no fue encontrado con id: " + command.tourId()));
        
        // Verificar que el tour pertenece al tour service correcto
        if (!tour.getTourServiceId().equals(command.tourServiceId())) {
            throw new IllegalArgumentException("El tour no pertenece al servicio de tour especificado");
        }
        
        UpdateTourDto dto = command.tourDto();
        
        tour.setStartDate(dto.getStartDate());
        tour.setEndDate(dto.getEndDate());
        tour.setTitle(dto.getTitle());
        tour.setPrice(dto.getPrice());
        tour.setPlace(dto.getPlace());
        tour.setCurrency(Currency.valueOf(dto.getCurrency()));
        tour.setStatus(ServiceStatus.valueOf(dto.getStatus()));
        
        return tourRepository.save(tour);
    }
}
