package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FlightBookingDto {
    
    @NotBlank(message = "Origin is required")
    private String origin;
    
    @NotBlank(message = "Destiny is required")
    private String destiny;
    
    @NotNull(message = "Departure date is required")
    private LocalDateTime departureDate;
    
    @NotNull(message = "Arrival date is required")
    private LocalDateTime arrivalDate;
    
    @NotBlank(message = "Aeroline is required")
    private String aeroline;
    
    @NotBlank(message = "Aeroline booking code is required")
    private String aerolineBookingCode;
    
    private String costamarBookingCode;
    
    @NotBlank(message = "TKT numbers are required")
    private String tktNumbers;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    private Float totalPrice;
    
    @NotBlank(message = "Currency is required")
    private String currency;
}
