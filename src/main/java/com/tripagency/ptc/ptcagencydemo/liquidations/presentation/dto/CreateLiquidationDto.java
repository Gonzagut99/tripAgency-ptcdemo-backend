package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateLiquidationDto {
    
    @Positive(message = "Currency rate must be greater than zero")
    @NotNull(message = "Currency rate is required")
    private Float currencyRate;
    
    @NotNull(message = "Payment deadline is required")
    private LocalDateTime paymentDeadline;
    
    @PositiveOrZero(message = "Companion count cannot be negative")
    @NotNull(message = "Companion count is required")
    private Integer companion;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Staff ID is required")
    private Long staffId;
}
