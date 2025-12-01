package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateHotelBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.HotelBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.ServiceStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IHotelBookingJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateHotelBookingDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Currency;

@Service
public class UpdateHotelBookingCommandHandler {
    
    private final IHotelBookingJpaRepository hotelBookingRepository;
    
    public UpdateHotelBookingCommandHandler(IHotelBookingJpaRepository hotelBookingRepository) {
        this.hotelBookingRepository = hotelBookingRepository;
    }
    
    @Transactional
    public HotelBooking execute(UpdateHotelBookingCommand command) {
        HotelBooking hotelBooking = hotelBookingRepository.findById(command.hotelBookingId())
                .orElseThrow(() -> new IllegalArgumentException("La reserva de hotel no fue encontrada con id: " + command.hotelBookingId()));
        
        // Verificar que la reserva pertenece al hotel service correcto
        if (!hotelBooking.getHotelServiceId().equals(command.hotelServiceId())) {
            throw new IllegalArgumentException("La reserva no pertenece al servicio de hotel especificado");
        }
        
        UpdateHotelBookingDto dto = command.hotelBookingDto();
        
        hotelBooking.setCheckIn(dto.getCheckIn());
        hotelBooking.setCheckOut(dto.getCheckOut());
        hotelBooking.setHotel(dto.getHotel());
        hotelBooking.setRoom(dto.getRoom());
        hotelBooking.setRoomDescription(dto.getRoomDescription());
        hotelBooking.setPriceByNight(dto.getPriceByNight());
        hotelBooking.setCurrency(Currency.valueOf(dto.getCurrency()));
        hotelBooking.setStatus(ServiceStatus.valueOf(dto.getStatus()));
        
        return hotelBookingRepository.save(hotelBooking);
    }
}
