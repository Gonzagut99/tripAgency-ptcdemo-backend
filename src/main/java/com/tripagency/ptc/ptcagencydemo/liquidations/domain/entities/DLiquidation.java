package com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentStatus;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DLiquidation extends BaseAbstractDomainEntity {
    private float currencyRate;
    private float totalAmount;
    private LocalDateTime paymentDeadline;
    private int companion;
    private DLiquidationStatus status;
    private List<DPayment> payments;
    private DPaymentStatus paymentStatus;
    private List<DFlightService> flightServices;
    private List<DHotelService> hotelServices;
    private List<DTourService> tourServices;
    private List<DAdditionalServices> additionalServices;
    private Long customerId;
    private Optional<DCustomer> customer;
    private Long staffId;
    private Optional<DStaff> staffOnCharge;
    private List<DIncidency> incidencies;

    public DLiquidation(float currencyRate, LocalDateTime paymentDeadline, int companion,
            Long customerId, Long staffId) {
        super();
        validateLiquidation(currencyRate, paymentDeadline, companion, customerId, staffId);

        this.currencyRate = currencyRate;
        this.paymentDeadline = paymentDeadline;
        this.companion = companion;
        this.customerId = customerId;
        this.staffId = staffId;
        this.status = DLiquidationStatus.IN_QUOTE;
        this.paymentStatus = DPaymentStatus.PENDING;
        this.totalAmount = 0f;
        this.payments = new ArrayList<>();
        this.flightServices = new ArrayList<>();
        this.hotelServices = new ArrayList<>();
        this.tourServices = new ArrayList<>();
        this.additionalServices = new ArrayList<>();
        this.incidencies = new ArrayList<>();
    }

    private void validateLiquidation(float currencyRate, LocalDateTime paymentDeadline, int companion,
            Long customerId, Long staffId) {
        if (currencyRate <= 0) {
            throw new IllegalArgumentException("El tipo de cambio debe ser mayor que cero");
        }
        if (paymentDeadline == null) {
            throw new IllegalArgumentException("La fecha límite de pago no puede ser nula");
        }
        if (companion < 0) {
            throw new IllegalArgumentException("El número de acompañantes no puede ser negativo");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        if (staffId == null) {
            throw new IllegalArgumentException("El ID del personal no puede ser nulo");
        }
    }

    // Flight Service Management
    public void addFlightService(DFlightService flightService) {
        if (flightService == null) {
            throw new IllegalArgumentException("El servicio de vuelo no puede ser nulo");
        }
        this.flightServices.add(flightService);
        recalculateTotal();
    }

    public void removeFlightService(DFlightService flightService) {
        this.flightServices.remove(flightService);
        recalculateTotal();
    }

    // Hotel Service Management
    public void addHotelService(DHotelService hotelService) {
        if (hotelService == null) {
            throw new IllegalArgumentException("El servicio de hotel no puede ser nulo");
        }
        this.hotelServices.add(hotelService);
        recalculateTotal();
    }

    public void removeHotelService(DHotelService hotelService) {
        this.hotelServices.remove(hotelService);
        recalculateTotal();
    }

    // Tour Service Management
    public void addTourService(DTourService tourService) {
        if (tourService == null) {
            throw new IllegalArgumentException("El servicio de tour no puede ser nulo");
        }
        this.tourServices.add(tourService);
        recalculateTotal();
    }

    public void removeTourService(DTourService tourService) {
        this.tourServices.remove(tourService);
        recalculateTotal();
    }

    // Additional Service Management
    public void addAdditionalService(DAdditionalServices additionalService) {
        if (additionalService == null) {
            throw new IllegalArgumentException("El servicio adicional no puede ser nulo");
        }
        this.additionalServices.add(additionalService);
        recalculateTotal();
    }

    public void removeAdditionalService(DAdditionalServices additionalService) {
        this.additionalServices.remove(additionalService);
        recalculateTotal();
    }

    // Payment Management
    public void addPayment(DPayment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("El pago no puede ser nulo");
        }
        this.payments.add(payment);
        updatePaymentStatus();
    }

    public void removePayment(DPayment payment) {
        this.payments.remove(payment);
        updatePaymentStatus();
    }

    // Incidency Management
    public void addIncidency(DIncidency incidency) {
        if (incidency == null) {
            throw new IllegalArgumentException("La incidencia no puede ser nula");
        }
        this.incidencies.add(incidency);
    }

    // Business Logic Methods
    public float calculateTotal() {
        float total = 0f;

        // Sum all flight services
        if (flightServices != null) {
            total += flightServices.stream()
                    .map(DFlightService::calculateTotal)
                    .reduce(0f, Float::sum);
        }

        // Sum all hotel services
        if (hotelServices != null) {
            total += hotelServices.stream()
                    .map(DHotelService::calculateTotal)
                    .reduce(0f, Float::sum);
        }

        // Sum all tour services
        if (tourServices != null) {
            total += tourServices.stream()
                    .map(DTourService::calculateTotal)
                    .reduce(0f, Float::sum);
        }

        // Sum all additional services
        if (additionalServices != null) {
            total += additionalServices.stream()
                    .map(DAdditionalServices::calculateTotal)
                    .reduce(0f, Float::sum);
        }

        return total;
    }

    private void recalculateTotal() {
        this.totalAmount = calculateTotal();
    }

    private void updatePaymentStatus() {
        if (payments == null || payments.isEmpty()) {
            this.paymentStatus = DPaymentStatus.PENDING;
            return;
        }

        float totalPaid = payments.stream()
                .filter(DPayment::isValid)
                .map(DPayment::getAmount)
                .reduce(0f, Float::sum);

        if (totalPaid >= this.totalAmount) {
            this.paymentStatus = DPaymentStatus.COMPLETED;
        } else if (totalPaid > 0) {
            this.paymentStatus = DPaymentStatus.ON_COURSE;
        } else {
            this.paymentStatus = DPaymentStatus.PENDING;
        }
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(this.paymentDeadline)
                && this.paymentStatus != DPaymentStatus.COMPLETED;
    }

    public void closeLiquidation() {
        if (this.paymentStatus != DPaymentStatus.COMPLETED) {
            throw new IllegalStateException("No se puede cerrar la liquidación con pagos incompletos");
        }
        if (this.status == DLiquidationStatus.COMPLETED) {
            throw new IllegalStateException("La liquidación ya está cerrada");
        }
        this.status = DLiquidationStatus.COMPLETED;
    }

    public void startProcessing() {
        if (this.status != DLiquidationStatus.IN_QUOTE && this.status != DLiquidationStatus.PENDING) {
            throw new IllegalStateException("No se puede iniciar el procesamiento desde el estado actual");
        }
        this.status = DLiquidationStatus.ON_COURSE;
    }

    public void markAsPending() {
        if (this.status != DLiquidationStatus.IN_QUOTE) {
            throw new IllegalStateException("Solo se puede marcar como pendiente desde el estado IN_QUOTE");
        }
        this.status = DLiquidationStatus.PENDING;
    }

    public float getTotalPaid() {
        if (payments == null || payments.isEmpty()) {
            return 0f;
        }
        return payments.stream()
                .filter(DPayment::isValid)
                .map(DPayment::getAmount)
                .reduce(0f, Float::sum);
    }

    public float getRemainingAmount() {
        return Math.max(0, this.totalAmount - getTotalPaid());
    }

    public boolean hasServices() {
        return (flightServices != null && !flightServices.isEmpty())
                || (hotelServices != null && !hotelServices.isEmpty())
                || (tourServices != null && !tourServices.isEmpty())
                || (additionalServices != null && !additionalServices.isEmpty());
    }
}
