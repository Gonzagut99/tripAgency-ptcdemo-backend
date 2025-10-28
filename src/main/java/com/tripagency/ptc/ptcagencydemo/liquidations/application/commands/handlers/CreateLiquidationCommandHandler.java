package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.CreateLiquidationCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;

@Service
public class CreateLiquidationCommandHandler {

    private final ILiquidationRepository liquidationRepository;

    @Autowired
    public CreateLiquidationCommandHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }

    @Transactional
    public DLiquidation execute(CreateLiquidationCommand command) {
        DLiquidation liquidation = new DLiquidation(
                command.currencyRate(),
                command.paymentDeadline(),
                command.companion(),
                command.customerId(),
                command.staffId());

        return liquidationRepository.save(liquidation);
    }
}
