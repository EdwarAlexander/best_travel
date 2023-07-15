package com.debugeando.test.best_travel.domain.repositories;

import com.debugeando.test.best_travel.domain.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
