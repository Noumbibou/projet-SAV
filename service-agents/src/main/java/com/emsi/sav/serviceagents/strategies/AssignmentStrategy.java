package com.emsi.sav.serviceagents.strategies;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.events.TicketCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AssignmentStrategy {
    Agent selectAgent(TicketCreatedEvent ticket, List<Agent> availableAgents);
}