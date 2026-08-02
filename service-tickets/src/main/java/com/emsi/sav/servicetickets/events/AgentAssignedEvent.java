package com.emsi.sav.servicetickets.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgentAssignedEvent(
        UUID ticketId,
        UUID agentId,
        String agentName,
        LocalDateTime assignedAt
) {
}