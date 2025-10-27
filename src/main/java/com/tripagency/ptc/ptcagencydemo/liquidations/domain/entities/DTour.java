package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import java.time.LocalDateTime;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DServiceStatus;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTour extends BaseAbstractDomainEntity {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private float price;
    private String place;
    private DCurrency currency;
    private DServiceStatus status;
    private Long tourServiceId;

    public DTour(LocalDateTime startDate, LocalDateTime endDate, String title,
            float price, String place, DCurrency currency, Long tourServiceId) {
        super();
        validateTour(startDate, endDate, title, price, place, currency, tourServiceId);

        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.price = price;
        this.place = place;
        this.currency = currency;
        this.status = DServiceStatus.PENDING;
        this.tourServiceId = tourServiceId;
    }

    private void validateTour(LocalDateTime startDate, LocalDateTime endDate, String title,
            float price, String place, DCurrency currency, Long tourServiceId) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after or equal to start date");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (place == null || place.trim().isEmpty()) {
            throw new IllegalArgumentException("Place cannot be null or empty");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        // tourServiceId can be null temporarily when creating the tour before
        // persisting
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
