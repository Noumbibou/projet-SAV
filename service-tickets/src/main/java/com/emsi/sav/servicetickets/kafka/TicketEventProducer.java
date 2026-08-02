package com.emsi.sav.servicetickets.kafka;

import com.emsi.sav.servicetickets.events.TicketCreatedEvent;
import com.emsi.sav.servicetickets.events.TicketResolvedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketEventProducer {

    private static final String TOPIC_TICKET_CREATED = "ticket-created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_TICKET_RESOLVED = "ticket-resolved";


    public TicketEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publierTicketCree(TicketCreatedEvent event){
        kafkaTemplate.send(TOPIC_TICKET_CREATED, event.ticketId().toString(), event);
    }

    public void publierTicketResolu(TicketResolvedEvent event) {
        kafkaTemplate.send(TOPIC_TICKET_RESOLVED, event.ticketId().toString(), event);
    }
}
