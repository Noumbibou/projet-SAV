package com.emsi.sav.serviceagents.kafka;

import com.emsi.sav.serviceagents.events.AgentAssignedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

@Component
public class AgentEventProducer {

    private static final String TOPIC_AGENT_ASSIGNED = "agent-assigned";

    private final KafkaTemplate<String, AgentAssignedEvent> kafkaTemplate;

    public AgentEventProducer(KafkaTemplate<String, AgentAssignedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publierAgentAssigne(AgentAssignedEvent event){
        kafkaTemplate.send(TOPIC_AGENT_ASSIGNED, event.ticketId().toString(), event);
    }
}
