package com.emsi.sav.servicetickets.repositories;

import com.emsi.sav.servicetickets.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByStatus(String status);

    Page<Ticket> findByStatus(String status, Pageable pageable);

    List<Ticket> findByCustomerId(UUID customerId);

    List<Ticket> findByAgentId(UUID agentId);
}
