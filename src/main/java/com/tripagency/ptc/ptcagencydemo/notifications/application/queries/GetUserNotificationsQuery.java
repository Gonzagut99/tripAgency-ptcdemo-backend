package com.tripagency.ptc.ptcagencydemo.notifications.application.queries;

import com.tripagency.ptc.ptcagencydemo.notifications.presentation.dto.PaginatedNotificationRequestDto;

public record GetUserNotificationsQuery(Long userId, PaginatedNotificationRequestDto requestDto) {
}
