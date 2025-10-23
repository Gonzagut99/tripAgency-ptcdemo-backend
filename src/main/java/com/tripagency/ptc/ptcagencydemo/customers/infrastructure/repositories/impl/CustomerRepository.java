package com.tripagency.ptc.ptcagencydemo.customers.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.customers.domain.repositories.ICustomerRepository;
import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.entities.Customer;
import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.mappers.CustomerLombokMapper;
import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.repositories.interfaces.ICustomerJpaRepository;

@Repository
public class CustomerRepository implements ICustomerRepository {
    private final ICustomerJpaRepository jpaRepository;
    private final CustomerLombokMapper mapper;

    public CustomerRepository(ICustomerJpaRepository jpaRepository, CustomerLombokMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public DCustomer save(DCustomer customer) {
        Customer rCustomer = this.mapper.toPersistence(customer);
        rCustomer = jpaRepository.save(rCustomer);
        return this.mapper.toDomain(rCustomer);
    }

    @Override
    public DCustomer update(DCustomer customer) {
        Customer rCustomer = this.mapper.toPersistence(customer);
        rCustomer = jpaRepository.save(rCustomer);
        return this.mapper.toDomain(rCustomer);
    }

    @Override
    public DCustomer findById(Long id) {
        return jpaRepository.findById(id).map(this.mapper::toDomain).orElse(null);
    }

    @Override
    public Page<DCustomer> findAll(int page, int size) {
        return jpaRepository.findAll(PageRequest.of(page, size)).map(this.mapper::toDomain);
    }

    @Override
    public DCustomer deleteById(Long id) {
        DCustomer customer = findById(id);
        jpaRepository.deleteById(id);
        return customer;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
