package com.tripagency.ptc.ptcagencydemo.customers.infrastructure.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.entities.Customer;

public interface ICustomerJpaRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
