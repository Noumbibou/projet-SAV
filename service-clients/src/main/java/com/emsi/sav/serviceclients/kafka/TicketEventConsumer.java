package com.emsi.sav.serviceclients.kafka;

import com.emsi.sav.serviceclients.events.TicketCreatedEvent;
import com.emsi.sav.serviceclients.events.TicketResolvedEvent;
import com.emsi.sav.serviceclients.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketEventConsumer {

    private final NotificationService notificationService;

    public TicketEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "ticket-created", groupId = "service-clients")
    public void ecouterTicketCree(TicketCreatedEvent event) {
        notificationService.notifierCreationTicket(event);
    }

    @KafkaListener(topics = "ticket-resolved", groupId = "service-clients")
    public void ecouterTicketResolu(TicketResolvedEvent event) {
        notificationService.notifierResolutionTicket(event);
    }
}