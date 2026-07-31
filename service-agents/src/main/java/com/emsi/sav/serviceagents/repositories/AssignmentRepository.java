package com.emsi.sav.serviceagents.repositories;

import com.emsi.sav.serviceagents.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByAgentId(UUID agentId);
}