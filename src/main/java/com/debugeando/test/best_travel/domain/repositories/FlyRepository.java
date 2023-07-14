package com.debugeando.test.best_travel.domain.repositories;

import com.debugeando.test.best_travel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlyRepository extends JpaRepository<FlyEntity,Long> {
}
