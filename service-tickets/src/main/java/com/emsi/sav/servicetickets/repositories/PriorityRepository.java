package com.emsi.sav.servicetickets.repositories;

import com.emsi.sav.servicetickets.entities.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, UUID> {
    Optional<Priority> findByLabel(String label);
}
