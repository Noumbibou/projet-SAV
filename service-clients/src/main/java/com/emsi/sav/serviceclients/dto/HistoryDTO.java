package com.emsi.sav.serviceclients.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistoryDTO(
        UUID id,
        UUID customerId,
        UUID ticketId,
        String description,
        LocalDateTime date
) {
}