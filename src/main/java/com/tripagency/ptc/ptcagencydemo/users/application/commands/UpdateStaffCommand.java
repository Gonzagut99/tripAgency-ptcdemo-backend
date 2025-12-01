package com.tripagency.ptc.ptcagencydemo.users.application.commands;

import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.UpdateStaffDto;

public record UpdateStaffCommand(Long staffId, UpdateStaffDto dto) {
}
