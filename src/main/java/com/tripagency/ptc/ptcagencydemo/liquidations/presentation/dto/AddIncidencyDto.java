package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddIncidencyDto {
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    @PositiveOrZero(message = "Amount cannot be negative")
    private Float amount;
    
    @NotNull(message = "Incidency date is required")
    private LocalDateTime incidencyDate;
}
