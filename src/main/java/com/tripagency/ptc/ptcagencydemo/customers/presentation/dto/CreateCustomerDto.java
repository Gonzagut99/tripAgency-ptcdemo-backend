package com.tripagency.ptc.ptcagencydemo.customers.presentation.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.tripagency.ptc.ptcagencydemo.customers.domain.enums.DIdDocumentType;

import lombok.Getter;

@Getter
public class CreateCustomerDto {
    private String firstName;
    private String lastName;
    private Optional<String> email;
    private Optional<String> phoneNumber;
    private LocalDate birthDate;
    private DIdDocumentType idDocumentType;
    private String idDocumentNumber;
    private Optional<String> address;
    private Optional<String> nationality;
}
