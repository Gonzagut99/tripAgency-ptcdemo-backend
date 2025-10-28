package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddTourServiceDto;

public record AddTourServiceCommand(
    Long liquidationId,
    AddTourServiceDto tourServiceDto
) {}
