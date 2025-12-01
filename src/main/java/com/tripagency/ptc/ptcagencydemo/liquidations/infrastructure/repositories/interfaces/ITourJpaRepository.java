package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Tour;

@Repository
public interface ITourJpaRepository extends JpaRepository<Tour, Long> {
    
    List<Tour> findByTourServiceId(Long tourServiceId);
}
