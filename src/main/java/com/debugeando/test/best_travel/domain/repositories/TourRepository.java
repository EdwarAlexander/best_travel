package com.debugeando.test.best_travel.domain.repositories;

import com.debugeando.test.best_travel.domain.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
}
