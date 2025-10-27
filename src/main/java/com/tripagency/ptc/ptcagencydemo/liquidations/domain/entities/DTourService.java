package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DTourService extends DBaseAbstractService {
    private List<DTour> tours;

    public DTourService(float tariffRate, boolean isTaxed, DCurrency currency, Long liquidationId) {
        super(tariffRate, isTaxed, currency, liquidationId);
        this.tours = new ArrayList<>();
    }

    public void addTour(DTour tour) {
        if (tour == null) {
            throw new IllegalArgumentException("Tour cannot be null");
        }
        this.tours.add(tour);
    }

    public void removeTour(DTour tour) {
        this.tours.remove(tour);
    }

    @Override
    public float calculateTotal() {
        if (tours == null || tours.isEmpty()) {
            return 0f;
        }
        
        float baseTotal = tours.stream()
            .map(DTour::getPrice)
            .reduce(0f, Float::sum);
        
        return applyTariffAndTax(baseTotal);
    }

    public boolean hasTours() {
        return tours != null && !tours.isEmpty();
    }

    public int getTourCount() {
        return tours != null ? tours.size() : 0;
    }
}
