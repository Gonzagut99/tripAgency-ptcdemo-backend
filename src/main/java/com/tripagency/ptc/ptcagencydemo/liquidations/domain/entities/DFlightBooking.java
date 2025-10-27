package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import java.time.LocalDateTime;
import java.util.Optional;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DServiceStatus;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DFlightBooking extends BaseAbstractDomainEntity {
    private String origin;
    private String destiny;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String aeroline;
    private String aerolineBookingCode;
    private Optional<String> costamarBookingCode;
    private String tktNumbers;
    private DServiceStatus status;
    private float totalPrice;
    private DCurrency currency;
    private Long flightServiceId;

    public DFlightBooking(String origin, String destiny, LocalDateTime departureDate, 
                         LocalDateTime arrivalDate, String aeroline, String aerolineBookingCode,
                         Optional<String> costamarBookingCode, String tktNumbers, 
                         float totalPrice, DCurrency currency, Long flightServiceId) {
        super();
        validateFlightBooking(origin, destiny, departureDate, arrivalDate, aeroline, 
                            aerolineBookingCode, tktNumbers, totalPrice, currency, flightServiceId);
        
        this.origin = origin;
        this.destiny = destiny;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.aeroline = aeroline;
        this.aerolineBookingCode = aerolineBookingCode;
        this.costamarBookingCode = costamarBookingCode != null ? costamarBookingCode : Optional.empty();
        this.tktNumbers = tktNumbers;
        this.status = DServiceStatus.PENDING;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.flightServiceId = flightServiceId;
    }

    private void validateFlightBooking(String origin, String destiny, LocalDateTime departureDate,
                                      LocalDateTime arrivalDate, String aeroline, String aerolineBookingCode,
                                      String tktNumbers, float totalPrice, DCurrency currency, Long flightServiceId) {
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }
        if (destiny == null || destiny.trim().isEmpty()) {
            throw new IllegalArgumentException("Destiny cannot be null or empty");
        }
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date cannot be null");
        }
        if (arrivalDate == null) {
            throw new IllegalArgumentException("Arrival date cannot be null");
        }
        if (arrivalDate.isBefore(departureDate)) {
            throw new IllegalArgumentException("Arrival date must be after departure date");
        }
        if (aeroline == null || aeroline.trim().isEmpty()) {
            throw new IllegalArgumentException("Aeroline cannot be null or empty");
        }
        if (aerolineBookingCode == null || aerolineBookingCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Aeroline booking code cannot be null or empty");
        }
        if (tktNumbers == null || tktNumbers.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket numbers cannot be null or empty");
        }
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        // flightServiceId can be null temporarily when creating the booking before persisting
    }

    public void complete() {
        this.status = DServiceStatus.COMPLETED;
    }

    public void cancel() {
        this.status = DServiceStatus.CANCELED;
    }

    public boolean isPending() {
        return this.status == DServiceStatus.PENDING;
    }
}
