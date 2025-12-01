package com.tripagency.ptc.ptcagencydemo.auth.application.commands;

/**
 * Command for user logout.
 */
public record LogoutCommand(
    String accessToken,
    Long userId
) {}
