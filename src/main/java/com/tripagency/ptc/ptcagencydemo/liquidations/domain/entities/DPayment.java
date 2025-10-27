package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentMethod;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentValidity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPayment extends BaseAbstractDomainEntity {
    private DPaymentMethod method;
    private float amount;
    private Long liquidationId;
    private DPaymentValidity validationStatus;

    public DPayment(DPaymentMethod method, float amount, Long liquidationId) {
        super();
        validatePayment(method, amount, liquidationId);
        
        this.method = method;
        this.amount = amount;
        this.liquidationId = liquidationId;
        this.validationStatus = DPaymentValidity.PENDING;
    }

    private void validatePayment(DPaymentMethod method, float amount, Long liquidationId) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        if (liquidationId == null) {
            throw new IllegalArgumentException("Liquidation ID cannot be null");
        }
    }

    public void markAsValid() {
        this.validationStatus = DPaymentValidity.VALID;
    }

    public void markAsInvalid() {
        this.validationStatus = DPaymentValidity.INVALID;
    }

    public boolean isValid() {
        return this.validationStatus == DPaymentValidity.VALID;
    }

    public boolean isPending() {
        return this.validationStatus == DPaymentValidity.PENDING;
    }
}
