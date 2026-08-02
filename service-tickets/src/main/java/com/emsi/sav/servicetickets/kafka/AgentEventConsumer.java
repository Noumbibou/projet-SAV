package com.emsi.sav.servicetickets.kafka;

import com.emsi.sav.servicetickets.events.AgentAssignedEvent;
import com.emsi.sav.servicetickets.services.TicketService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AgentEventConsumer {

    private final TicketService ticketService;

    public AgentEventConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @KafkaListener(topics = "agent-assigned", groupId = "service-tickets")
    public void ecouterAgentAssigne(AgentAssignedEvent event) {
        ticketService.marquerAgentAssigne(event);
    }
}