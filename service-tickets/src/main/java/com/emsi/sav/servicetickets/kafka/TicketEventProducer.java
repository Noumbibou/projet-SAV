package com.emsi.sav.servicetickets.kafka;

import com.emsi.sav.servicetickets.events.TicketCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketEventProducer {

    private static final String TOPIC_TICKET_CREATED = "ticket-created";

    private final KafkaTemplate<String, TicketCreatedEvent> kafkaTemplate;

    public TicketEventProducer(KafkaTemplate<String, TicketCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publierTicketCree(TicketCreatedEvent event){
        kafkaTemplate.send(TOPIC_TICKET_CREATED, event.ticketId().toString(), event);
    }
}
