package com.emsi.sav.serviceagents.repositories;

import com.emsi.sav.serviceagents.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {
}