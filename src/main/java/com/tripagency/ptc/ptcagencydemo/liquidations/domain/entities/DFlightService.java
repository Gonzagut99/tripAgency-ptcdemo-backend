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
public class DFlightService extends DBaseAbstractService {
    private List<DFlightBooking> flightBookings;

    public DFlightService(float tariffRate, boolean isTaxed, DCurrency currency, Long liquidationId) {
        super(tariffRate, isTaxed, currency, liquidationId);
        this.flightBookings = new ArrayList<>();
    }

    public void addFlightBooking(DFlightBooking flightBooking) {
        if (flightBooking == null) {
            throw new IllegalArgumentException("Flight booking cannot be null");
        }
        this.flightBookings.add(flightBooking);
    }

    public void removeFlightBooking(DFlightBooking flightBooking) {
        this.flightBookings.remove(flightBooking);
    }

    @Override
    public float calculateTotal() {
        if (flightBookings == null || flightBookings.isEmpty()) {
            return 0f;
        }
        
        float baseTotal = flightBookings.stream()
            .map(DFlightBooking::getTotalPrice)
            .reduce(0f, Float::sum);
        
        return applyTariffAndTax(baseTotal);
    }

    public boolean hasBookings() {
        return flightBookings != null && !flightBookings.isEmpty();
    }

    public int getBookingCount() {
        return flightBookings != null ? flightBookings.size() : 0;
    }
}
