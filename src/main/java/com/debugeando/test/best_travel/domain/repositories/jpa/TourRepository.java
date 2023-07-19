package com.debugeando.test.best_travel.domain.repositories.jpa;

import com.debugeando.test.best_travel.domain.entities.jpa.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
}
