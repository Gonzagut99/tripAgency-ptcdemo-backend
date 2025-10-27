package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DIncidencyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class DIncidency extends BaseAbstractDomainEntity {
    private String reason;
    private Optional<Float> amount;
    private LocalDateTime incidencyDate;
    private DIncidencyStatus incidencyStatus;
    private Long liquidationId;

    public DIncidency(String reason, Optional<Float> amount, LocalDateTime incidencyDate, Long liquidationId) {
        super();
        validateIncidency(reason, amount, incidencyDate, liquidationId);
        
        this.reason = reason;
        this.amount = amount;
        this.incidencyDate = incidencyDate;
        this.incidencyStatus = DIncidencyStatus.PENDING;
        this.liquidationId = liquidationId;
    }

    private void validateIncidency(String reason, Optional<Float> amount, LocalDateTime incidencyDate, Long liquidationId) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be null or empty");
        }
        if (amount != null && amount.isPresent() && amount.get() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (incidencyDate == null) {
            throw new IllegalArgumentException("Incidency date cannot be null");
        }
        if (liquidationId == null) {
            throw new IllegalArgumentException("Liquidation ID cannot be null");
        }
    }

    public void approve() {
        if (this.incidencyStatus != DIncidencyStatus.PENDING) {
            throw new IllegalStateException("Only pending incidencies can be approved");
        }
        this.incidencyStatus = DIncidencyStatus.APPROVED;
    }

    public void reject() {
        if (this.incidencyStatus != DIncidencyStatus.PENDING) {
            throw new IllegalStateException("Only pending incidencies can be rejected");
        }
        this.incidencyStatus = DIncidencyStatus.REJECTED;
    }

    public boolean isPending() {
        return this.incidencyStatus == DIncidencyStatus.PENDING;
    }

    public boolean isApproved() {
        return this.incidencyStatus == DIncidencyStatus.APPROVED;
    }
}
