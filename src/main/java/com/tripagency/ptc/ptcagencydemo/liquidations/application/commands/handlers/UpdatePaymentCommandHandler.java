package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdatePaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.services.LiquidationTotalsService;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Payment;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.PaymentMethod;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.PaymentValidity;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IPaymentJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdatePaymentDto;

@Service
public class UpdatePaymentCommandHandler {
    
    private final IPaymentJpaRepository paymentRepository;
    private final LiquidationTotalsService liquidationTotalsService;
    
    public UpdatePaymentCommandHandler(
            IPaymentJpaRepository paymentRepository,
            LiquidationTotalsService liquidationTotalsService) {
        this.paymentRepository = paymentRepository;
        this.liquidationTotalsService = liquidationTotalsService;
    }
    
    @Transactional
    public Payment execute(UpdatePaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId())
                .orElseThrow(() -> new IllegalArgumentException("El pago no fue encontrado con id: " + command.paymentId()));
        
        // Verificar que el pago pertenece a la liquidación correcta
        if (!payment.getLiquidationId().equals(command.liquidationId())) {
            throw new IllegalArgumentException("El pago no pertenece a la liquidación especificada");
        }
        
        UpdatePaymentDto dto = command.paymentDto();
        
        payment.setMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        payment.setAmount(dto.getAmount());
        payment.setValidationStatus(PaymentValidity.valueOf(dto.getValidationStatus()));
        
        Payment saved = paymentRepository.save(payment);
        
        // Recalcular totales de la liquidación
        liquidationTotalsService.recalculateAndSaveTotals(command.liquidationId());
        
        return saved;
    }
}
