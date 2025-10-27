package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

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
public class AddAdditionalServiceDto {
    
    @NotNull(message = "Tariff rate is required")
    @Positive(message = "Tariff rate must be positive")
    private Float tariffRate;
    
    @NotNull(message = "Is taxed field is required")
    private Boolean isTaxed;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Float price;
    
    @NotBlank(message = "Status is required")
    private String status;
}
