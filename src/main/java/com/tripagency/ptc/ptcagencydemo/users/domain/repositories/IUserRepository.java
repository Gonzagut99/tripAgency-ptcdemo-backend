package com.tripagency.ptc.ptcagencydemo.users.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DUser;

public interface IUserRepository {
    DUser save(DUser user);
    DUser update(DUser user);
    DUser findById(Long id);
    DUser findByEmail(String email);
    DUser findByEmailIncludingInactive(String email);
    Page<DUser> findAll(Pageable pageConfig);
    DUser deleteById(Long id);
    boolean existsByEmail(String email);
}
