package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddFlightServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DFlightBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DFlightService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddFlightServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.FlightBookingDto;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;

@Service
public class AddFlightServiceCommandHandler {
    
    private final ILiquidationRepository liquidationRepository;
    
    public AddFlightServiceCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }
    
    @Transactional
    public DLiquidation execute(AddFlightServiceCommand command) {
        DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
                .orElseThrow(() -> new IllegalArgumentException("Liquidation not found with id: " + command.liquidationId()));
        
        AddFlightServiceDto dto = command.flightServiceDto();
        
        DFlightService flightService = new DFlightService(
                dto.getTariffRate(),
                dto.getIsTaxed(),
                DCurrency.valueOf(dto.getCurrency()),
                command.liquidationId()
        );
        
        for (FlightBookingDto bookingDto : dto.getFlightBookings()) {
            DFlightBooking booking = new DFlightBooking(
                    bookingDto.getOrigin(),
                    bookingDto.getDestiny(),
                    bookingDto.getDepartureDate(),
                    bookingDto.getArrivalDate(),
                    bookingDto.getAeroline(),
                    bookingDto.getAerolineBookingCode(),
                    Optional.ofNullable(bookingDto.getCostamarBookingCode()),
                    bookingDto.getTktNumbers(),
                    bookingDto.getTotalPrice(),
                    DCurrency.valueOf(bookingDto.getCurrency()),
                    null
            );
            flightService.addFlightBooking(booking);
        }
        
        liquidation.addFlightService(flightService);
        
        return liquidationRepository.save(liquidation);
    }
}
