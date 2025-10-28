package com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.users.application.queries.StaffPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.repositories.IStaffRepository;

@Service
public class StaffPaginatedQueryHandler {
    private final IStaffRepository staffRepository;

    public StaffPaginatedQueryHandler(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Page<DStaff> execute(StaffPaginatedQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize()
        );
        return staffRepository.findAll(pageable);
    }
}
