package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DAdditionalServices;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DFlightService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DHotelService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DIncidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DPayment;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DTourService;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentStatus;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class LiquidationWithDetailsDto {
    private Long id;
    private Long customerId;
    private Long staffId;
    private DCustomer customer;
    private DStaff staffOnCharge;
    private float currencyRate;
    private float totalAmount;
    private LocalDateTime paymentDeadline;
    private int companion;
    private DLiquidationStatus status;
    private DPaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DPayment> payments;
    private List<DFlightService> flightServices;
    private List<DHotelService> hotelServices;
    private List<DTourService> tourServices;
    private List<DAdditionalServices> additionalServices;
    private List<DIncidency> incidencies;
}
