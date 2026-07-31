package com.emsi.sav.serviceagents.repositories;

import com.emsi.sav.serviceagents.entities.Workload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkloadRepository extends JpaRepository<Workload, UUID> {
    Optional<Workload> findByAgentId(UUID agentId);
}