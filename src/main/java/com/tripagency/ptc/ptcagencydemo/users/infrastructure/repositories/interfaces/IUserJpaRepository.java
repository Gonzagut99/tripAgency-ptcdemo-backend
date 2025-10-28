package com.tripagency.ptc.ptcagencydemo.users.infrastructure.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.users.infrastructure.entities.User;

@Repository
public interface IUserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
