package com.emsi.sav.serviceagents.services;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.entities.Skill;
import com.emsi.sav.serviceagents.entities.SkillType;
import com.emsi.sav.serviceagents.repositories.AgentRepository;
import com.emsi.sav.serviceagents.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final AgentRepository agentRepository;

    public SkillService(SkillRepository skillRepository, AgentRepository agentRepository) {
        this.skillRepository = skillRepository;
        this.agentRepository = agentRepository;
    }

    public Skill ajouterCompetence(UUID agentId, SkillType typeCompetence) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("Agent introuvable: " + agentId));

        Skill skill = new Skill(null, typeCompetence, agent);
        return skillRepository.save(skill);
    }

    public void supprimerCompetence(UUID skillId) {
        if (!skillRepository.existsById(skillId)) {
            throw new IllegalArgumentException("Competence introuvable: " + skillId);
        }
        skillRepository.deleteById(skillId);
    }
}