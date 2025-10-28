package com.tripagency.ptc.ptcagencydemo.users.application.events;

public record UserCreatedDomainEvent(Long userId, String message) {
}
