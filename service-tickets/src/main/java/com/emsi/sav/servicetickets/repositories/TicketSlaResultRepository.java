package com.emsi.sav.servicetickets.repositories;

import com.emsi.sav.servicetickets.entities.TicketSlaResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketSlaResultRepository extends JpaRepository<TicketSlaResult, UUID> {
    boolean existsByTicketId(UUID ticketId);
}
