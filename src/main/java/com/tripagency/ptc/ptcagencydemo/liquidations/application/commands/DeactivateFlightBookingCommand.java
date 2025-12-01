package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

public record DeactivateFlightBookingCommand(Long liquidationId, Long flightServiceId, Long flightBookingId) {
}
