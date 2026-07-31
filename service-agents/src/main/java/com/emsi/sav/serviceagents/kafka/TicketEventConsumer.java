package com.emsi.sav.serviceagents.kafka;

import com.emsi.sav.serviceagents.events.TicketCreatedEvent;
import com.emsi.sav.serviceagents.services.AgentAssignmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketEventConsumer {

    private final AgentAssignmentService agentAssignmentService;

    public TicketEventConsumer(AgentAssignmentService agentAssignmentService) {
        this.agentAssignmentService = agentAssignmentService;
    }

    @KafkaListener(topics = "ticket-created", groupId = "service-agents")
    public void ecouterTicketCree(TicketCreatedEvent event) {
        agentAssignmentService.traiterNouveauTicket(event);
    }
}