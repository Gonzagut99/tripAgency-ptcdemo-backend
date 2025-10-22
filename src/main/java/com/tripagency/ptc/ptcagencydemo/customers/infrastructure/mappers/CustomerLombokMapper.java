package com.tripagency.ptc.ptcagencydemo.customers.infrastructure.mappers;

import java.util.Optional;

import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.customers.domain.enums.DIdDocumentType;
import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.entities.Customer;
import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.enums.IdDocumentType;

public class CustomerLombokMapper implements ICustomerLombokMapper {

    @Override
    public Customer toPersistence(DCustomer domainCustomer) {
        if (domainCustomer == null) {
            return null;
        }
        Customer.CustomerBuilder customerBuilder = Customer.builder();
        customerBuilder.firstName(domainCustomer.getFirstName());
        customerBuilder.lastName(domainCustomer.getLastName());
        customerBuilder.email(domainCustomer.getEmail().orElse(null));
        customerBuilder.phoneNumber(domainCustomer.getPhoneNumber().orElse(null));
        customerBuilder.birthDate(domainCustomer.getBirthDate());
        customerBuilder.idDocumentType(
            IdDocumentType.valueOf(domainCustomer.getIdDocumentType().name())
        );
        customerBuilder.idDocumentNumber(domainCustomer.getIdDocumentNumber());
        customerBuilder.address(domainCustomer.getAddress().orElse(null));
        customerBuilder.nationality(domainCustomer.getNationality().orElse(null));
        if (domainCustomer.getIsActive() != null) {
            customerBuilder.isActive(domainCustomer.getIsActive());
        }
        return customerBuilder.build();
    }

    @Override
    public DCustomer toDomain(Customer persistenceCustomer) {
        if (persistenceCustomer == null) {
            return null;
        }
        DCustomer domainCustomer = new DCustomer();
        // domainCustomer.setId(persistenceCustomer.getId());
        domainCustomer.setFirstName(persistenceCustomer.getFirstName());
        domainCustomer.setLastName(persistenceCustomer.getLastName());
        domainCustomer.setEmail(Optional.ofNullable(persistenceCustomer.getEmail()));
        domainCustomer.setPhoneNumber(Optional.ofNullable(persistenceCustomer.getPhoneNumber()));
        domainCustomer.setBirthDate(persistenceCustomer.getBirthDate());
        domainCustomer.setIdDocumentType(
            DIdDocumentType.valueOf(persistenceCustomer.getIdDocumentType().name())
        );
        domainCustomer.setIdDocumentNumber(persistenceCustomer.getIdDocumentNumber());
        domainCustomer.setAddress(Optional.ofNullable(persistenceCustomer.getAddress()));
        domainCustomer.setNationality(Optional.ofNullable(persistenceCustomer.getNationality()));
        domainCustomer.setId(persistenceCustomer.getId());
        domainCustomer.setCreatedDate(persistenceCustomer.getCreatedDate());
        domainCustomer.setIsActive(persistenceCustomer.getIsActive());
        return domainCustomer;
    }
    
}
