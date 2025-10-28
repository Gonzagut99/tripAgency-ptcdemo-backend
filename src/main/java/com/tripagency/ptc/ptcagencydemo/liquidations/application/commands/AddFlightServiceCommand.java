package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddFlightServiceDto;

public record AddFlightServiceCommand(
    Long liquidationId,
    AddFlightServiceDto flightServiceDto
) {}
