package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddHotelServiceDto;

public record AddHotelServiceCommand(
    Long liquidationId,
    AddHotelServiceDto hotelServiceDto
) {}
