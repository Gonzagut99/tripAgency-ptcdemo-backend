package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateAdditionalServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.services.LiquidationTotalsService;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.AdditionalServices;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.enums.ServiceStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces.IAdditionalServicesJpaRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateAdditionalServiceDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Currency;

@Service
public class UpdateAdditionalServiceCommandHandler {
    
    private final IAdditionalServicesJpaRepository additionalServicesRepository;
    private final LiquidationTotalsService liquidationTotalsService;
    
    public UpdateAdditionalServiceCommandHandler(
            IAdditionalServicesJpaRepository additionalServicesRepository,
            LiquidationTotalsService liquidationTotalsService) {
        this.additionalServicesRepository = additionalServicesRepository;
        this.liquidationTotalsService = liquidationTotalsService;
    }
    
    @Transactional
    public AdditionalServices execute(UpdateAdditionalServiceCommand command) {
        AdditionalServices additionalService = additionalServicesRepository.findById(command.additionalServiceId())
                .orElseThrow(() -> new IllegalArgumentException("El servicio adicional no fue encontrado con id: " + command.additionalServiceId()));
        
        // Verificar que el servicio pertenece a la liquidación correcta
        if (!additionalService.getLiquidationId().equals(command.liquidationId())) {
            throw new IllegalArgumentException("El servicio adicional no pertenece a la liquidación especificada");
        }
        
        UpdateAdditionalServiceDto dto = command.additionalServiceDto();
        
        additionalService.setTariffRate(dto.getTariffRate());
        additionalService.setTaxed(dto.getIsTaxed());
        additionalService.setCurrency(Currency.valueOf(dto.getCurrency()));
        additionalService.setPrice(dto.getPrice());
        additionalService.setStatus(ServiceStatus.valueOf(dto.getStatus()));
        
        AdditionalServices saved = additionalServicesRepository.save(additionalService);
        
        // Recalcular totales de la liquidación
        liquidationTotalsService.recalculateAndSaveTotals(command.liquidationId());
        
        return saved;
    }
}
