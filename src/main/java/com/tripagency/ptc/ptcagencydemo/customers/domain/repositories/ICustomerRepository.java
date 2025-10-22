package com.tripagency.ptc.ptcagencydemo.customers.domain.repositories;

import org.springframework.data.domain.Page;

import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;

public interface ICustomerRepository {
    DCustomer save(DCustomer customer);
    DCustomer update(DCustomer customer);
    DCustomer findById(Long id);
    Page<DCustomer> findAll(int pageNumber, int pageSize);
    DCustomer deleteById(Long id);
}
