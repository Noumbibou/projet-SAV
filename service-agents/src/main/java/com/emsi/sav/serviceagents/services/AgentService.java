package com.emsi.sav.serviceagents.services;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.entities.Workload;
import com.emsi.sav.serviceagents.repositories.AgentRepository;
import com.emsi.sav.serviceagents.repositories.WorkloadRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final WorkloadRepository workloadRepository;

    public AgentService(AgentRepository agentRepository, WorkloadRepository workloadRepository) {
        this.agentRepository = agentRepository;
        this.workloadRepository = workloadRepository;
    }

    public Agent creerAgent(Agent agent) {
        Agent agentSauvegarde = agentRepository.save(agent);

        Workload workload = new Workload(null, agentSauvegarde, 0, 5);
        workloadRepository.save(workload);

        return agentSauvegarde;
    }

    public Agent mettreAJourAgent(UUID id, Agent donneesModifiees) {
        Agent agentExistant = agentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agent introuvable: " + id));

        agentExistant.setName(donneesModifiees.getName());
        agentExistant.setEmail(donneesModifiees.getEmail());

        return agentRepository.save(agentExistant);
    }

    public void supprimerAgent(UUID id) {
        if (!agentRepository.existsById(id)) {
            throw new IllegalArgumentException("Agent introuvable: " + id);
        }
        agentRepository.deleteById(id);
    }
}