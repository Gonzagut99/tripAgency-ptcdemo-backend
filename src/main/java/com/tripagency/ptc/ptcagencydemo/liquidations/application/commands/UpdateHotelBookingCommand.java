package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateHotelBookingDto;

public record UpdateHotelBookingCommand(
    Long liquidationId,
    Long hotelServiceId,
    Long hotelBookingId,
    UpdateHotelBookingDto hotelBookingDto
) {}
