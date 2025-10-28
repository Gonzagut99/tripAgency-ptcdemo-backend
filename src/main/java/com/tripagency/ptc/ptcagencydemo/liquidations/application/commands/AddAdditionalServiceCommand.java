package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddAdditionalServiceDto;

public record AddAdditionalServiceCommand(
    Long liquidationId,
    AddAdditionalServiceDto additionalServiceDto
) {}
