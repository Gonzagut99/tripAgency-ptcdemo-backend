package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

public record DeactivateHotelBookingCommand(Long liquidationId, Long hotelServiceId, Long hotelBookingId) {
}
