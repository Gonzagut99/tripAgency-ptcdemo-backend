package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddIncidencyDto;

public record AddIncidencyCommand(
    Long liquidationId,
    AddIncidencyDto incidencyDto
) {}
