package com.tripagency.ptc.ptcagencydemo.users.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DRoles;

public interface IStaffRepository {
    DStaff save(DStaff staff);
    DStaff update(DStaff staff);
    DStaff findById(Long id);
    DStaff findByUserId(Long userId);
    List<DStaff> findByRole(DRoles role);
    Page<DStaff> findAll(Pageable pageConfig);
    DStaff deleteById(Long id);
}
