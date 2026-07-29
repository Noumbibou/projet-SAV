package com.emsi.sav.servicetickets.repositories;

import com.emsi.sav.servicetickets.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByStatus(String status);

    List<Ticket> findByAgentId(UUID agentId);

    List<Ticket> findByCustomerId(UUID customerId);
}
