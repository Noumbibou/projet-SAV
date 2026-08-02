package com.emsi.sav.serviceclients.repositories;

import com.emsi.sav.serviceclients.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    List<History> findByCustomerId(UUID customerId);
    boolean existsByTicketIdAndDescriptionStartingWith(UUID ticketId, String prefix);

}