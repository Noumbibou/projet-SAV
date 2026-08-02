package com.emsi.sav.serviceclients.controllers;

import com.emsi.sav.serviceclients.dto.HistoryDTO;
import com.emsi.sav.serviceclients.repositories.HistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryRepository historyRepository;

    public HistoryController(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<HistoryDTO>> historiqueParCustomer(@PathVariable("customerId") UUID customerId) {
        List<HistoryDTO> historique = historyRepository.findByCustomerId(customerId).stream()
                .map(h -> new HistoryDTO(h.getId(), h.getCustomer().getId(), h.getTicketId(), h.getDescription(), h.getDate()))
                .toList();
        return ResponseEntity.ok(historique);
    }
}