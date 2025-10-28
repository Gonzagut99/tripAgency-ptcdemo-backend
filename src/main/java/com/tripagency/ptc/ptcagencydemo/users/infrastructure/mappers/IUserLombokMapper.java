package com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DUser;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.entities.User;

@Mapper
public interface IUserLombokMapper {
    @Mapping(target = "staff", ignore = true)
    User toPersistence(DUser domainUser);

    DUser toDomain(User persistenceUser);

    // Helper methods for Optional<String> conversions
    default String mapOptionalStringToString(Optional<String> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }

    default Optional<String> mapStringToOptionalString(String value) {
        return Optional.ofNullable(value);
    }
}
