package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

public record DeactivateAdditionalServiceCommand(Long liquidationId, Long additionalServiceId) {
}
