package com.debugeando.test.best_travel.domain.repositories;

import com.debugeando.test.best_travel.domain.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
}
