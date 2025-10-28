package com.tripagency.ptc.ptcagencydemo.notifications.application.queries.handlers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.notifications.application.queries.GetUserNotificationsQuery;
import com.tripagency.ptc.ptcagencydemo.notifications.domain.entities.DUserNotification;
import com.tripagency.ptc.ptcagencydemo.notifications.domain.repositories.IUserNotificationRepository;

@Service
public class GetUserNotificationsQueryHandler {
    
    private final IUserNotificationRepository userNotificationRepository;

    public GetUserNotificationsQueryHandler(IUserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    public Page<DUserNotification> execute(GetUserNotificationsQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize(),
            Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return userNotificationRepository.findByUserId(query.userId(), pageable);
    }
}
