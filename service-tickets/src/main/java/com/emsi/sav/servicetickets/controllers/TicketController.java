package com.emsi.sav.servicetickets.controllers;

import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.repositories.TicketRepository;
import com.emsi.sav.servicetickets.services.SuggestionService;
import com.emsi.sav.servicetickets.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final SuggestionService suggestionService;

    public TicketController(TicketService ticketService, TicketRepository ticketRepository, SuggestionService suggestionService) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseEntity<Ticket> creerTicket(@RequestBody Ticket ticket){
        Ticket ticketCree = ticketService.creerTicket(ticket);
        return ResponseEntity.ok(ticketCree);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listeTousTicket(){
        return ResponseEntity.ok(ticketService.tousLesTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(ticketService.afficheTicket(id));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<Ticket>> getAllTicketByStatus(@PathVariable("status") String status){
        return ResponseEntity.ok(ticketService.getAllByStatus(status));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Ticket>> ticketParClient(@PathVariable("customerId") UUID customerId){
        return ResponseEntity.ok(ticketRepository.findByCustomerId(customerId));
    }

    @GetMapping("/agent/agentId")
    public ResponseEntity<List<Ticket>> ticketParAgent(@PathVariable("agentId") UUID agentId){
        return ResponseEntity.ok(ticketRepository.findByAgentId(agentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> mettreAjourTicket(@PathVariable("id") UUID id, @RequestBody Ticket ticket){
        return ResponseEntity.ok(ticketService.mettreAJourTicket(id, ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTicket(@PathVariable("id") UUID id){
        ticketService.supprimerTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/resoudre")
    public ResponseEntity<Ticket> resoudreTicket(@PathVariable("id") UUID id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ticketService.resoudreTicket(id, body.get("description")));
    }

    @GetMapping("/{id}/suggestion")
    public ResponseEntity<Map<String, String>> obtenirSuggestion(@PathVariable("id") UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket introuvable: " + id));

        String suggestion = suggestionService.genererSuggestion(ticket);
        return ResponseEntity.ok(Map.of("suggestion", suggestion));
    }
}
