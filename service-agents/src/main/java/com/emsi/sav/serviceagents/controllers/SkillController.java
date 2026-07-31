package com.emsi.sav.serviceagents.controllers;

import com.emsi.sav.serviceagents.entities.Skill;
import com.emsi.sav.serviceagents.entities.SkillType;
import com.emsi.sav.serviceagents.services.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/agents/{agentId}/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Skill> ajouterCompetence(@PathVariable("agentId") UUID agentId, @RequestBody Map<String, String> body) {
        SkillType type = SkillType.valueOf(body.get("name").toUpperCase());
        Skill skill = skillService.ajouterCompetence(agentId, type);
        return ResponseEntity.ok(skill);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> supprimerCompetence(@PathVariable("skillId") UUID skillId) {
        skillService.supprimerCompetence(skillId);
        return ResponseEntity.noContent().build();
    }
}