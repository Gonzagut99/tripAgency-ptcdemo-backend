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
public class HotelBookingDto {
    
    @NotNull(message = "Check-in date is required")
    private LocalDateTime checkIn;
    
    @NotNull(message = "Check-out date is required")
    private LocalDateTime checkOut;
    
    @NotBlank(message = "Hotel name is required")
    private String hotel;
    
    @NotBlank(message = "Room is required")
    private String room;
    
    private String roomDescription;
    
    @NotNull(message = "Price by night is required")
    @Positive(message = "Price by night must be positive")
    private Float priceByNight;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotBlank(message = "Status is required")
    private String status;
}
