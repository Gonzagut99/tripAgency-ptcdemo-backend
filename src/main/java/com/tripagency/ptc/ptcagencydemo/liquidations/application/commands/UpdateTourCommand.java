package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateTourDto;

public record UpdateTourCommand(
    Long liquidationId,
    Long tourServiceId,
    Long tourId,
    UpdateTourDto tourDto
) {}
