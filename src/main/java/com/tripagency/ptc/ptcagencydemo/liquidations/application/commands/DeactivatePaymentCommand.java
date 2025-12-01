package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

public record DeactivatePaymentCommand(Long liquidationId, Long paymentId) {
}
