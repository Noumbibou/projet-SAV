package com.emsi.sav.servicetickets.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketCreatedEvent(
        UUID ticketId,
        String title,
        String channel,
        String priorityLabel,
        UUID customerId,
        LocalDateTime createAt

){}


