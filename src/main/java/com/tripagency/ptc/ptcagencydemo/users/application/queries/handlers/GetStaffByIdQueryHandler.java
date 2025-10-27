package com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.users.application.queries.GetStaffByIdQuery;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.repositories.IStaffRepository;

@Service
public class GetStaffByIdQueryHandler {
    private final IStaffRepository staffRepository;

    public GetStaffByIdQueryHandler(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public DStaff execute(GetStaffByIdQuery query) {
        DStaff staff = staffRepository.findById(query.staffId());
        if (staff == null) {
            throw new IllegalArgumentException("No se encontró el staff con ID: " + query.staffId());
        }
        return staff;
    }
}
