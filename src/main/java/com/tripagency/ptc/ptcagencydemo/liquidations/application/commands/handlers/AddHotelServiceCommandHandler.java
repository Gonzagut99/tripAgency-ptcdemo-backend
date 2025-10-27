package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddHotelServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DHotelBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DHotelService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddHotelServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.HotelBookingDto;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;

@Service
public class AddHotelServiceCommandHandler {
    
    private final ILiquidationRepository liquidationRepository;
    
    public AddHotelServiceCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }
    
    @Transactional
    public DLiquidation execute(AddHotelServiceCommand command) {
        DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
                .orElseThrow(() -> new IllegalArgumentException("Liquidation not found with id: " + command.liquidationId()));
        
        AddHotelServiceDto dto = command.hotelServiceDto();
        
        DHotelService hotelService = new DHotelService(
                dto.getTariffRate(),
                dto.getIsTaxed(),
                DCurrency.valueOf(dto.getCurrency()),
                command.liquidationId()
        );
        
        for (HotelBookingDto bookingDto : dto.getHotelBookings()) {
            DHotelBooking booking = new DHotelBooking(
                    bookingDto.getCheckIn(),
                    bookingDto.getCheckOut(),
                    bookingDto.getHotel(),
                    bookingDto.getRoom(),
                    Optional.ofNullable(bookingDto.getRoomDescription()),
                    bookingDto.getPriceByNight(),
                    DCurrency.valueOf(bookingDto.getCurrency()),
                    null
            );
            hotelService.addHotelBooking(booking);
        }
        
        liquidation.addHotelService(hotelService);
        
        return liquidationRepository.save(liquidation);
    }
}
