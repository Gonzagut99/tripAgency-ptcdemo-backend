package com.tripagency.ptc.ptcagencydemo.users.infrastructure.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.users.infrastructure.entities.Staff;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Roles;

@Repository
public interface IStaffJpaRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUserId(Long userId);
    List<Staff> findByRole(Roles role);
}
