package com.emsi.sav.serviceagents.controllers;

import com.emsi.sav.serviceagents.entities.Workload;
import com.emsi.sav.serviceagents.repositories.WorkloadRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workloads")
public class WorkloadController {

    private final WorkloadRepository workloadRepository;

    public WorkloadController(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    @GetMapping
    public ResponseEntity<List<Workload>> listerWorkloads() {
        return ResponseEntity.ok(workloadRepository.findAll());
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Workload> workloadParAgent(@PathVariable("agentId") UUID agentId) {
        return workloadRepository.findByAgentId(agentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}