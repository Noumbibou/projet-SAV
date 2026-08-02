package com.emsi.sav.serviceclients.controllers;

import com.emsi.sav.serviceclients.entities.SatisfactionSurvey;
import com.emsi.sav.serviceclients.repositories.SatisfactionSurveyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/surveys")
public class SatisfactionSurveyController {

    private final SatisfactionSurveyRepository satisfactionSurveyRepository;

    public SatisfactionSurveyController(SatisfactionSurveyRepository satisfactionSurveyRepository) {
        this.satisfactionSurveyRepository = satisfactionSurveyRepository;
    }

    @GetMapping
    public ResponseEntity<List<SatisfactionSurvey>> listerSurveys() {
        return ResponseEntity.ok(satisfactionSurveyRepository.findAll());
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<SatisfactionSurvey> consulterParTicket(@PathVariable("ticketId") UUID ticketId) {
        return satisfactionSurveyRepository.findByTicketId(ticketId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/repondre")
    public ResponseEntity<SatisfactionSurvey> repondreEnquete(@PathVariable("id") UUID id,
                                                              @RequestBody Map<String, Object> body) {
        SatisfactionSurvey survey = satisfactionSurveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enquete introuvable: " + id));

        survey.setScore((Integer) body.get("score"));
        survey.setComment((String) body.get("comment"));
        survey.setRespondedAt(LocalDateTime.now());

        return ResponseEntity.ok(satisfactionSurveyRepository.save(survey));
    }
}