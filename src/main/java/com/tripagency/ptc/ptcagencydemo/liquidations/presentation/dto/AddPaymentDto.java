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
    
    @NotNull(message = "El método de pago es obligatorio")
    private String paymentMethod;

    @Positive(message = "El monto debe ser mayor que cero")
    @NotNull(message = "El monto es obligatorio")
    private Float amount;

    private String currency = "PEN";

    private String evidenceUrl;
}
