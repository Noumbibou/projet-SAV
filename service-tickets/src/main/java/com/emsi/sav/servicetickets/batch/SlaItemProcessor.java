package com.emsi.sav.servicetickets.batch;

import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.entities.TicketSlaResult;
import com.emsi.sav.servicetickets.repositories.TicketSlaResultRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class SlaItemProcessor implements ItemProcessor<Ticket, TicketSlaResult> {

    private final TicketSlaResultRepository resultRepository;
    private final SlaThresholds slaThresholds;

    public SlaItemProcessor(TicketSlaResultRepository resultRepository, SlaThresholds slaThresholds) {
        this.resultRepository = resultRepository;
        this.slaThresholds = slaThresholds;
    }

    @Override
    public TicketSlaResult process(Ticket ticket) {
        if (ticket.getResolvedAt() == null || ticket.getCreatedAt() == null) {
            return null;
        }
        if (resultRepository.existsByTicketId(ticket.getId())) {
            return null;
        }

        long seuilHeures = slaThresholds.getSeuilHeures(ticket.getPriority().getLabel());
        double dureeHeures = Duration.between(ticket.getCreatedAt(), ticket.getResolvedAt()).toMinutes() / 60.0;
        boolean respecte = dureeHeures <= seuilHeures;

        return new TicketSlaResult(
                null,
                ticket.getId(),
                ticket.getPriority().getLabel(),
                dureeHeures,
                seuilHeures,
                respecte,
                LocalDateTime.now()
        );
    }
}