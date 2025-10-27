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
public class DHotelBooking extends BaseAbstractDomainEntity {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String hotel;
    private String room;
    private Optional<String> roomDescription;
    private float priceByNight;
    private DCurrency currency;
    private DServiceStatus status;
    private Long hotelServiceId;

    public DHotelBooking(LocalDateTime checkIn, LocalDateTime checkOut, String hotel,
                        String room, Optional<String> roomDescription, float priceByNight,
                        DCurrency currency, Long hotelServiceId) {
        super();
        validateHotelBooking(checkIn, checkOut, hotel, room, priceByNight, currency, hotelServiceId);
        
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hotel = hotel;
        this.room = room;
        this.roomDescription = roomDescription != null ? roomDescription : Optional.empty();
        this.priceByNight = priceByNight;
        this.currency = currency;
        this.status = DServiceStatus.PENDING;
        this.hotelServiceId = hotelServiceId;
    }

    private void validateHotelBooking(LocalDateTime checkIn, LocalDateTime checkOut, String hotel,
                                     String room, float priceByNight, DCurrency currency, Long hotelServiceId) {
        if (checkIn == null) {
            throw new IllegalArgumentException("Check-in date cannot be null");
        }
        if (checkOut == null) {
            throw new IllegalArgumentException("Check-out date cannot be null");
        }
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        if (hotel == null || hotel.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be null or empty");
        }
        if (room == null || room.trim().isEmpty()) {
            throw new IllegalArgumentException("Room cannot be null or empty");
        }
        if (priceByNight < 0) {
            throw new IllegalArgumentException("Price by night cannot be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        // hotelServiceId can be null temporarily when creating the booking before persisting
    }

    public float calculateTotalPrice() {
        long nights = java.time.Duration.between(checkIn, checkOut).toDays();
        return priceByNight * nights;
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
