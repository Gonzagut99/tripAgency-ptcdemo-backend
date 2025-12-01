package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateIncidencyDto;

public record UpdateIncidencyCommand(
    Long liquidationId,
    Long incidencyId,
    UpdateIncidencyDto incidencyDto
) {}
