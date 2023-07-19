package com.debugeando.test.best_travel.domain.repositories.jpa;

import com.debugeando.test.best_travel.domain.entities.jpa.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
