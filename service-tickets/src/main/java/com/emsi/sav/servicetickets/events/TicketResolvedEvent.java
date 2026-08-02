package com.emsi.sav.servicetickets.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResolvedEvent(
        UUID ticketId,
        UUID customerId,
        String resolutionDescription,
        LocalDateTime resolvedAt
) {
}