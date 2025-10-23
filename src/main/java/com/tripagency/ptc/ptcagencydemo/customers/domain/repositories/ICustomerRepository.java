package com.tripagency.ptc.ptcagencydemo.customers.domain.repositories;

import org.springframework.data.domain.Page;

import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;

public interface ICustomerRepository {
    DCustomer save(DCustomer customer);
    DCustomer update(DCustomer customer);
    DCustomer findById(Long id);
    Page<DCustomer> findAll(int page, int size);
    DCustomer deleteById(Long id);
    boolean existsByEmail(String email);
}
