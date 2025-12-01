package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

public record DeactivateTourCommand(Long liquidationId, Long tourServiceId, Long tourId) {
}
