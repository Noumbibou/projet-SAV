package com.emsi.sav.serviceagents.controllers;

import com.emsi.sav.serviceagents.entities.Assignment;
import com.emsi.sav.serviceagents.repositories.AssignmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> listerAssignments() {
        return ResponseEntity.ok(assignmentRepository.findAll());
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Assignment>> assignmentsParAgent(@PathVariable("agentId") UUID agentId) {
        return ResponseEntity.ok(assignmentRepository.findByAgentId(agentId));
    }
}