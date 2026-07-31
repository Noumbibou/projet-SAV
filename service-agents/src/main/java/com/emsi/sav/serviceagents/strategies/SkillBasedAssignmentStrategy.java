package com.emsi.sav.serviceagents.strategies;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.entities.Workload;
import com.emsi.sav.serviceagents.events.TicketCreatedEvent;
import com.emsi.sav.serviceagents.repositories.WorkloadRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SkillBasedAssignmentStrategy implements AssignmentStrategy {

    private final WorkloadRepository workloadRepository;

    public SkillBasedAssignmentStrategy(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    @Override
    public Agent selectAgent(TicketCreatedEvent ticket, List<Agent> availableAgents) {
        return availableAgents.stream()
                .filter(agent -> possedeCompetence(agent, ticket.categoryName()))
                .min(Comparator.comparingInt(this::getChargeActuelle))
                .orElseThrow(() -> new IllegalStateException("Aucun agent disponible avec la competence : " + ticket.categoryName()));
    }

    private boolean possedeCompetence(Agent agent, String categoryName) {
        return agent.getSkills().stream()
                .anyMatch(skill -> skill.getName().name().equalsIgnoreCase(categoryName));
    }

    private int getChargeActuelle(Agent agent){
        return workloadRepository.findByAgentId(agent.getId())
                .map(Workload::getCurrentLoad)
                .orElse(Integer.MAX_VALUE);
    }
}
