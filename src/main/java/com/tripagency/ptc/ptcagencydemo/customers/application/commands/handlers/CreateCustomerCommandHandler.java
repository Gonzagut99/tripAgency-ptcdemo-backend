package com.tripagency.ptc.ptcagencydemo.customers.application.commands.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.customers.application.commands.CreateCustomerCommand;
import com.tripagency.ptc.ptcagencydemo.customers.application.events.CustomerCreatedDomainEvent;
import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.customers.domain.repositories.ICustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateCustomerCommandHandler {
    private final ICustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public CreateCustomerCommandHandler(ICustomerRepository customerRepository, ApplicationEventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public DCustomer execute(CreateCustomerCommand command) {
        // Lógica para manejar el comando de creación de cliente
        // Por ejemplo, convertir el DTO a una entidad de dominio y guardarla
        DCustomer newCustomer = new DCustomer();
        newCustomer.setFirstName(command.customerDto().getFirstName());
        newCustomer.setLastName(command.customerDto().getLastName());
        newCustomer.setEmail(command.customerDto().getEmail());
        newCustomer.setPhoneNumber(command.customerDto().getPhoneNumber());
        newCustomer.setBirthDate(command.customerDto().getBirthDate());
        newCustomer.setIdDocumentType(command.customerDto().getIdDocumentType());
        newCustomer.setIdDocumentNumber(command.customerDto().getIdDocumentNumber());
        newCustomer.setAddress(command.customerDto().getAddress());
        newCustomer.setNationality(command.customerDto().getNationality());
        customerRepository.save(newCustomer);

        DCustomer savedCustomer = customerRepository.save(newCustomer);

        eventPublisher.publishEvent(new CustomerCreatedDomainEvent(savedCustomer.getId(), "Se creó un nuevo usuario"));

        return savedCustomer;
    }
}
