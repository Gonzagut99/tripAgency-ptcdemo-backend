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
public class TourDto {
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Float price;
    
    @NotBlank(message = "Place is required")
    private String place;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotBlank(message = "Status is required")
    private String status;
}
