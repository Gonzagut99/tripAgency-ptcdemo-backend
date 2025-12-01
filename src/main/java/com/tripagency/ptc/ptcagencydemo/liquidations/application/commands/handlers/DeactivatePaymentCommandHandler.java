package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivatePaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Payment;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.ILiquidationJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IPaymentJpaRepository;

import jakarta.transaction.Transactional;

@Service
public class DeactivatePaymentCommandHandler {
    private final IPaymentJpaRepository paymentJpaRepository;
    private final ILiquidationJpaRepository liquidationJpaRepository;

    public DeactivatePaymentCommandHandler(IPaymentJpaRepository paymentJpaRepository,
                                           ILiquidationJpaRepository liquidationJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.liquidationJpaRepository = liquidationJpaRepository;
    }

    @Transactional
    public Payment execute(DeactivatePaymentCommand command) {
        try {
            // Verify liquidation exists
            if (!liquidationJpaRepository.existsById(command.liquidationId())) {
                throw new IllegalArgumentException("No existe una liquidación con el ID: " + command.liquidationId());
            }

            Payment existingPayment = paymentJpaRepository.findById(command.paymentId())
                .orElseThrow(() -> new IllegalArgumentException("No existe un pago con el ID: " + command.paymentId()));

            existingPayment.setIsActive(false);
            return paymentJpaRepository.save(existingPayment);
        } catch (Exception e) {
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
