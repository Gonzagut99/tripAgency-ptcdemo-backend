package com.tripagency.ptc.ptcagencydemo.users.application.commands.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.UpdateStaffCommand;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DRoles;
import com.tripagency.ptc.ptcagencydemo.users.domain.repositories.IStaffRepository;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.UpdateStaffDto;

import jakarta.transaction.Transactional;

@Service
public class UpdateStaffCommandHandler {
    private final IStaffRepository staffRepository;

    public UpdateStaffCommandHandler(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Transactional
    public DStaff execute(UpdateStaffCommand command) {
        try {
            DStaff existingStaff = staffRepository.findById(command.staffId());
            if (existingStaff == null) {
                throw new IllegalArgumentException("No existe un staff con el ID: " + command.staffId());
            }

            UpdateStaffDto dto = command.dto();

            if (dto.getPhoneNumber() != null) {
                existingStaff.setPhoneNumber(dto.getPhoneNumber());
            }

            if (dto.getSalary() != null) {
                existingStaff.setSalary(dto.getSalary());
            }

            if (dto.getCurrency() != null) {
                existingStaff.setCurrency(DCurrency.valueOf(dto.getCurrency()));
            }

            if (dto.getRole() != null) {
                existingStaff.setRole(DRoles.valueOf(dto.getRole()));
            }

            if (dto.getHireDate() != null) {
                existingStaff.setHireDate(Optional.of(dto.getHireDate()));
            }

            return staffRepository.update(existingStaff);
        } catch (Exception e) {
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
