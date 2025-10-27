package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddPaymentDto {
    
    @NotNull(message = "Payment method is required")
    private String paymentMethod;
    
    @Positive(message = "Amount must be greater than zero")
    @NotNull(message = "Amount is required")
    private Float amount;
}
