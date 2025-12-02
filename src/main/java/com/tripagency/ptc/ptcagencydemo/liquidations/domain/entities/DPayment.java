package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DCurrency;
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
    private DCurrency currency;
    private Long liquidationId;
    private DPaymentValidity validationStatus;

    public DPayment(DPaymentMethod method, float amount, DCurrency currency, Long liquidationId) {
        super();
        validatePayment(method, amount, liquidationId);
        
        this.method = method;
        this.amount = amount;
        this.currency = currency != null ? currency : DCurrency.PEN;
        this.liquidationId = liquidationId;
        this.validationStatus = DPaymentValidity.PENDING;
    }

    private void validatePayment(DPaymentMethod method, float amount, Long liquidationId) {
        if (method == null) {
            throw new IllegalArgumentException("El metodo de pago no puede ser nulo");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor que cero");
        }
        if (liquidationId == null) {
            throw new IllegalArgumentException("El ID de liquidación no puede ser nulo");
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
