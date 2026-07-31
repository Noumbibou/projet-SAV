package com.emsi.sav.serviceagents.controllers;

import com.emsi.sav.serviceagents.entities.Agent;
import com.emsi.sav.serviceagents.repositories.AgentRepository;
import com.emsi.sav.serviceagents.services.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;
    private final AgentRepository agentRepository;

    public AgentController(AgentService agentService, AgentRepository agentRepository) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
    }

    @PostMapping
    public ResponseEntity<Agent> creerAgent(@RequestBody Agent agent) {
        return ResponseEntity.ok(agentService.creerAgent(agent));
    }

    @GetMapping
    public ResponseEntity<List<Agent>> listerAgents() {
        return ResponseEntity.ok(agentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> consulterAgent(@PathVariable("id") UUID id) {
        return agentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agent> mettreAJourAgent(@PathVariable("id") UUID id, @RequestBody Agent agent) {
        return ResponseEntity.ok(agentService.mettreAJourAgent(id, agent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAgent(@PathVariable("id") UUID id) {
        agentService.supprimerAgent(id);
        return ResponseEntity.noContent().build();
    }
}