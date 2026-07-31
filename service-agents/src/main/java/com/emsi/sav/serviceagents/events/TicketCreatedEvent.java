package com.emsi.sav.serviceagents.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketCreatedEvent(
        UUID ticketId,
        String title,
        String channel,
        String priorityLabel,
        String categoryName,
        UUID customerId,
        LocalDateTime createdAt
) {
}