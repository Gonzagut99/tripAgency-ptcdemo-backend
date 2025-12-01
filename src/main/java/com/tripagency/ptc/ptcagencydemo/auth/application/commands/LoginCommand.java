package com.tripagency.ptc.ptcagencydemo.auth.application.commands;

import com.tripagency.ptc.ptcagencydemo.auth.presentation.dto.LoginRequestDto;

/**
 * Command for user login.
 */
public record LoginCommand(
    LoginRequestDto loginDto,
    String userAgent,
    String ipAddress
) {}
