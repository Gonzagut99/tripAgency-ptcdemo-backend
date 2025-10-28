package com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.repositories.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.entities.UserNotification;

@Repository
public interface IUserNotificationJpaRepository extends JpaRepository<UserNotification, Long> {
    
    Page<UserNotification> findByUserId(Long userId, Pageable pageable);
    
    void deleteByNotificationId(Long notificationId);
}
