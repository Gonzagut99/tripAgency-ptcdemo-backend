package com.tripagency.ptc.ptcagencydemo.auth.application.commands;

/**
 * Command for refreshing access token.
 */
public record RefreshTokenCommand(
    String refreshToken,
    String userAgent,
    String ipAddress
) {}
