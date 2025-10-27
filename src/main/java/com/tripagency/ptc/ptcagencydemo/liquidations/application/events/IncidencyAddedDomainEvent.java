package com.tripagency.ptc.ptcagencydemo.liquidations.application.events;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record IncidencyAddedDomainEvent(
        Long liquidationId,
        Long staffId,
        String reason,
        String message) {
}
