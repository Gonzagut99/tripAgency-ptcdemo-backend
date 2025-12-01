package com.tripagency.ptc.ptcagencydemo.auth.application.commands;

import com.tripagency.ptc.ptcagencydemo.auth.presentation.dto.ChangePasswordRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command for changing user password.
 */
@Getter
@AllArgsConstructor
public class ChangePasswordCommand {
    private final Long userId;
    private final ChangePasswordRequestDto request;
}
