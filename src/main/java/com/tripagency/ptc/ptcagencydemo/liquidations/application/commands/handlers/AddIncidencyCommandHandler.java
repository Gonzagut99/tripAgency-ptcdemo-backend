package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.events.IncidencyAddedDomainEvent;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DIncidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddIncidencyDto;

@Service
public class AddIncidencyCommandHandler {

        private final ILiquidationRepository liquidationRepository;
        private final ApplicationEventPublisher eventPublisher;

        public AddIncidencyCommandHandler(
                        ILiquidationRepository liquidationRepository,
                        ApplicationEventPublisher eventPublisher) {
                this.liquidationRepository = liquidationRepository;
                this.eventPublisher = eventPublisher;
        }

        @Transactional
        public DLiquidation execute(AddIncidencyCommand command) {
                DLiquidation liquidation = liquidationRepository.findById(command.liquidationId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "La liquidación no fue encontrada con id: " + command.liquidationId()));

                AddIncidencyDto dto = command.incidencyDto();

                DIncidency incidency = new DIncidency(
                                dto.getReason(),
                                Optional.ofNullable(dto.getAmount()),
                                dto.getIncidencyDate(),
                                command.liquidationId());

                liquidation.addIncidency(incidency);
                DLiquidation savedLiquidation = liquidationRepository.save(liquidation);

                // Publicar evento de dominio
                String message = String.format("Nueva incidencia agregada a la liquidación #%d: %s",
                                command.liquidationId(), dto.getReason());
                eventPublisher.publishEvent(new IncidencyAddedDomainEvent(
                                command.liquidationId(),
                                liquidation.getStaffId(),
                                dto.getReason(),
                                message));

                return savedLiquidation;
        }
}
