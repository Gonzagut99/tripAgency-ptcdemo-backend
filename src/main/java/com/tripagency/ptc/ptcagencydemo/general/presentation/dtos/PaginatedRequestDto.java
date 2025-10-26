package com.tripagency.ptc.ptcagencydemo.general.presentation.dtos;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@TypeAlias("PaginatedRequestDto")
@Getter
@Setter
public class PaginatedRequestDto {
    @Nullable
    @Min(value = 1, message = "El nro de página debe ser al menos 1")
    private Integer page = 1;

    @Nullable
    @Min(value = 1, message = "El tamaño de la página debe ser al menos 1")
    private Integer size = 10;

    @Builder
    public PaginatedRequestDto(@Nullable Integer page, @Nullable Integer size) {
        if (page != null) {
            if (page-1 < 0) {
                this.page = 0;
            }
            this.page = page-1;
        }
        if (size != null) {
            this.size = size;
        }
    }

    public void normalizePageNumber(){
        if (this.page != null) {
            if (this.page-1 < 0) {
                this.page = 0;
            }
            this.page = page-1;
        }
    }
}
