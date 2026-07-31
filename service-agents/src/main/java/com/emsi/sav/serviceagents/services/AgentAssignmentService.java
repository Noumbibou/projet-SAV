package com.emsi.sav.serviceagents.services;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.entities.Assignment;
import com.emsi.sav.serviceagents.entities.Workload;
import com.emsi.sav.serviceagents.events.AgentAssignedEvent;
import com.emsi.sav.serviceagents.events.TicketCreatedEvent;
import com.emsi.sav.serviceagents.kafka.AgentEventProducer;
import com.emsi.sav.serviceagents.repositories.AgentRepository;
import com.emsi.sav.serviceagents.repositories.AssignmentRepository;
import com.emsi.sav.serviceagents.repositories.WorkloadRepository;
import com.emsi.sav.serviceagents.strategies.AssignmentStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgentAssignmentService {

    private final AgentRepository agentRepository;
    private final AssignmentRepository assignmentRepository;
    private final WorkloadRepository workloadRepository;
    private final AssignmentStrategy assignmentStrategy;
    private final AgentEventProducer agentEventProducer;

    public AgentAssignmentService(AgentRepository agentRepository, AssignmentRepository assignmentRepository, WorkloadRepository workloadRepository, AssignmentStrategy assignmentStrategy, AgentEventProducer agentEventProducer) {
        this.agentRepository = agentRepository;
        this.assignmentRepository = assignmentRepository;
        this.workloadRepository = workloadRepository;
        this.assignmentStrategy = assignmentStrategy;
        this.agentEventProducer = agentEventProducer;
    }

    public void traiterNouveauTicket(TicketCreatedEvent ticketEvent) {
        var agentsDisponibles = agentRepository.findAll();

        Agent agentChoisi = assignmentStrategy.selectAgent(ticketEvent, agentsDisponibles);

        Assignment assignment = new Assignment(
                null,
                ticketEvent.ticketId(),
                agentChoisi,
                LocalDateTime.now()
        );
        assignmentRepository.save(assignment);

        Workload workload = workloadRepository.findByAgentId(agentChoisi.getId())
                .orElseThrow(() -> new IllegalStateException("Workload introuvable pour l'agent: " + agentChoisi.getId()));
        workload.setCurrentLoad(workload.getCurrentLoad() + 1);
        workloadRepository.save(workload);

        AgentAssignedEvent event = new AgentAssignedEvent(
                ticketEvent.ticketId(),
                agentChoisi.getId(),
                agentChoisi.getName(),
                assignment.getAssignedAt()
        );
        agentEventProducer.publierAgentAssigne(event);
    }

}
