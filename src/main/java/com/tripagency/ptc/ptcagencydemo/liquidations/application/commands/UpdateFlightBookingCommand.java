package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateFlightBookingDto;

public record UpdateFlightBookingCommand(
    Long liquidationId,
    Long flightServiceId,
    Long flightBookingId,
    UpdateFlightBookingDto flightBookingDto
) {}
