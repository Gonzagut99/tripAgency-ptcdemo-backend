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
public class DHotelService extends DBaseAbstractService {
    private List<DHotelBooking> hotelBookings;

    public DHotelService(float tariffRate, boolean isTaxed, DCurrency currency, Long liquidationId) {
        super(tariffRate, isTaxed, currency, liquidationId);
        this.hotelBookings = new ArrayList<>();
    }

    public void addHotelBooking(DHotelBooking hotelBooking) {
        if (hotelBooking == null) {
            throw new IllegalArgumentException("Hotel booking cannot be null");
        }
        this.hotelBookings.add(hotelBooking);
    }

    public void removeHotelBooking(DHotelBooking hotelBooking) {
        this.hotelBookings.remove(hotelBooking);
    }

    @Override
    public float calculateTotal() {
        if (hotelBookings == null || hotelBookings.isEmpty()) {
            return 0f;
        }
        
        float baseTotal = hotelBookings.stream()
            .map(DHotelBooking::calculateTotalPrice)
            .reduce(0f, Float::sum);
        
        return applyTariffAndTax(baseTotal);
    }

    public boolean hasBookings() {
        return hotelBookings != null && !hotelBookings.isEmpty();
    }

    public int getBookingCount() {
        return hotelBookings != null ? hotelBookings.size() : 0;
    }
}
