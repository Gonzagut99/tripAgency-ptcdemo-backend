package com.tripagency.ptc.ptcagencydemo.customers.application.queries.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.customers.application.queries.CustomerPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.customers.domain.repositories.ICustomerRepository;
import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;

@Service
public class CustomerPaginatedQueryHandler {
    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerPaginatedQueryHandler(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<DCustomer> execute(CustomerPaginatedQuery query) {
        try {
            return customerRepository.findAll(query.paginationConfig().getPage(), query.paginationConfig().getSize());
        } catch (Exception e) {
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
