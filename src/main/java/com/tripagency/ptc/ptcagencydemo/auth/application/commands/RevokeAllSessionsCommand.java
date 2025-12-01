package com.tripagency.ptc.ptcagencydemo.auth.application.commands;

/**
 * Command for revoking all user sessions.
 */
public record RevokeAllSessionsCommand(
    Long userId
) {}
