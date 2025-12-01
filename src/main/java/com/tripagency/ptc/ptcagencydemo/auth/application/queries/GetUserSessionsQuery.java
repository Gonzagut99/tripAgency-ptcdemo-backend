package com.tripagency.ptc.ptcagencydemo.auth.application.queries;

/**
 * Query for getting user's active sessions.
 */
public record GetUserSessionsQuery(
    Long userId
) {}
