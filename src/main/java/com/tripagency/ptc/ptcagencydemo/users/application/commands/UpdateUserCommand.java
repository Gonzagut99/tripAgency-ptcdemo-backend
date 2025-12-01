package com.tripagency.ptc.ptcagencydemo.users.application.commands;

import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.UpdateUserDto;

public record UpdateUserCommand(Long userId, UpdateUserDto dto) {
}
