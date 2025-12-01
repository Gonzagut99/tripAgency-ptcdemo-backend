package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateAdditionalServiceDto;

public record UpdateAdditionalServiceCommand(
    Long liquidationId,
    Long additionalServiceId,
    UpdateAdditionalServiceDto additionalServiceDto
) {}
