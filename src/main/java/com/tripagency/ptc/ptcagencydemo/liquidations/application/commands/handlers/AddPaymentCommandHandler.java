package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddPaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DPayment;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;

@Service
public class AddPaymentCommandHandler {
    
    private final ILiquidationRepository liquidationRepository;
    
    public AddPaymentCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }
    
    @Transactional
    public DLiquidation execute(AddPaymentCommand command) {
        DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
            .orElseThrow(() -> new IllegalArgumentException("La liquidación no fue encontrada con id: " + command.liquidationId()));
        
        DPayment payment = new DPayment(
            command.paymentMethod(),
            command.amount(),
            command.liquidationId()
        );
        
        liquidation.addPayment(payment);
        
        return liquidationRepository.save(liquidation);
    }
}
