package com.emsi.sav.serviceclients.repositories;

import com.emsi.sav.serviceclients.entities.SatisfactionSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SatisfactionSurveyRepository extends JpaRepository<SatisfactionSurvey, UUID> {
    Optional<SatisfactionSurvey> findByTicketId(UUID ticketId);

}
