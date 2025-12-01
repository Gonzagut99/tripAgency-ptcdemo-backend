package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateFlightBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.FlightBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.ServiceStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IFlightBookingJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateFlightBookingDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Currency;

@Service
public class UpdateFlightBookingCommandHandler {
    
    private final IFlightBookingJpaRepository flightBookingRepository;
    
    public UpdateFlightBookingCommandHandler(IFlightBookingJpaRepository flightBookingRepository) {
        this.flightBookingRepository = flightBookingRepository;
    }
    
    @Transactional
    public FlightBooking execute(UpdateFlightBookingCommand command) {
        FlightBooking flightBooking = flightBookingRepository.findById(command.flightBookingId())
                .orElseThrow(() -> new IllegalArgumentException("La reserva de vuelo no fue encontrada con id: " + command.flightBookingId()));
        
        // Verificar que la reserva pertenece al flight service correcto
        if (!flightBooking.getFlightServiceId().equals(command.flightServiceId())) {
            throw new IllegalArgumentException("La reserva no pertenece al servicio de vuelo especificado");
        }
        
        UpdateFlightBookingDto dto = command.flightBookingDto();
        
        flightBooking.setOrigin(dto.getOrigin());
        flightBooking.setDestiny(dto.getDestiny());
        flightBooking.setDepartureDate(dto.getDepartureDate());
        flightBooking.setArrivalDate(dto.getArrivalDate());
        flightBooking.setAeroline(dto.getAeroline());
        flightBooking.setAerolineBookingCode(dto.getAerolineBookingCode());
        flightBooking.setCostamarBookingCode(dto.getCostamarBookingCode());
        flightBooking.setTktNumbers(dto.getTktNumbers());
        flightBooking.setTotalPrice(dto.getTotalPrice());
        flightBooking.setCurrency(Currency.valueOf(dto.getCurrency()));
        flightBooking.setStatus(ServiceStatus.valueOf(dto.getStatus()));
        
        return flightBookingRepository.save(flightBooking);
    }
}
