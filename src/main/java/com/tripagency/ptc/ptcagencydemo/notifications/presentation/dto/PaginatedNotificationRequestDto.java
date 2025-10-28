package com.tripagency.ptc.ptcagencydemo.notifications.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tripagency.ptc.ptcagencydemo.general.presentation.dtos.PaginatedRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class PaginatedNotificationRequestDto extends PaginatedRequestDto {
    
    public PaginatedNotificationRequestDto(int page, int size) {
        super(page, size);
    }
}
