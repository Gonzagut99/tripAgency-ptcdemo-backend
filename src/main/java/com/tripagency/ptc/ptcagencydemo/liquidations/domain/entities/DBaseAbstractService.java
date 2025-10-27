package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class DBaseAbstractService extends BaseAbstractDomainEntity {
    private float tariffRate;
    private boolean isTaxed;
    private DCurrency currency;
    private Long liquidationId;

    protected DBaseAbstractService(float tariffRate, boolean isTaxed, DCurrency currency, Long liquidationId) {
        super();
        validateBaseService(tariffRate, currency, liquidationId);
        
        this.tariffRate = tariffRate;
        this.isTaxed = isTaxed;
        this.currency = currency;
        this.liquidationId = liquidationId;
    }

    private void validateBaseService(float tariffRate, DCurrency currency, Long liquidationId) {
        if (tariffRate < 0) {
            throw new IllegalArgumentException("Tariff rate cannot be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if (liquidationId == null) {
            throw new IllegalArgumentException("Liquidation ID cannot be null");
        }
    }

    public abstract float calculateTotal();

    protected float applyTariffAndTax(float baseAmount) {
        float withTariff = baseAmount + (baseAmount * tariffRate / 100);
        if (isTaxed) {
            // Aplicar IGV (18% en Perú)
            return withTariff * 1.18f;
        }
        return withTariff;
    }
}
