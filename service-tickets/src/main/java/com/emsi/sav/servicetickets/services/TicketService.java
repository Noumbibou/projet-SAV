package com.emsi.sav.servicetickets.services;

import com.emsi.sav.servicetickets.entities.Resolution;
import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.events.AgentAssignedEvent;
import com.emsi.sav.servicetickets.events.TicketCreatedEvent;
import com.emsi.sav.servicetickets.events.TicketResolvedEvent;
import com.emsi.sav.servicetickets.kafka.TicketEventProducer;
import com.emsi.sav.servicetickets.repositories.ResolutionRepository;
import com.emsi.sav.servicetickets.repositories.TicketRepository;
import com.emsi.sav.servicetickets.strategies.CategoryStrategy;
import com.emsi.sav.servicetickets.strategies.PriorityStrategy;
import jakarta.ws.rs.NotFoundException;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PriorityStrategy priorityStrategy;
    private final TicketEventProducer ticketEventProducer;
    private final CategoryStrategy categoryStrategy;
    private final ResolutionRepository resolutionRepository;

    public TicketService(TicketRepository ticketRepository, PriorityStrategy priorityStrategy, TicketEventProducer ticketEventProducer, CategoryStrategy categoryStrategy, ResolutionRepository resolutionRepository) {
        this.ticketRepository = ticketRepository;
        this.priorityStrategy = priorityStrategy;
        this.ticketEventProducer = ticketEventProducer;
        this.categoryStrategy = categoryStrategy;
        this.resolutionRepository = resolutionRepository;
    }

    public Ticket creerTicket(Ticket ticket) {
        ticket.setStatus("NOUVEAU");
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setPriority(priorityStrategy.calculerPriorite(ticket));
        ticket.setCategory(categoryStrategy.calculerCategorie(ticket));
        Ticket ticketSauvegarde = ticketRepository.save(ticket);

        TicketCreatedEvent event = new TicketCreatedEvent(
                ticketSauvegarde.getId(),
                ticketSauvegarde.getTitle(),
                ticketSauvegarde.getChannel(),
                ticketSauvegarde.getPriority().getLabel(),
                ticketSauvegarde.getCategory().getName(),
                ticketSauvegarde.getCustomerId(),
                ticketSauvegarde.getCreatedAt()
        );

        ticketEventProducer.publierTicketCree(event);

        return ticketSauvegarde;
    }

    public Ticket afficheTicket(UUID id){
        return ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("Ticket introuvable"));
    }

    public List<Ticket> tousLesTickets(){
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllByStatus(String status){
        return ticketRepository.findByStatus(status);
    }

    public Ticket mettreAJourTicket(UUID id, Ticket donneesModifiees) {
        Ticket ticketExistant = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket introuvable: " + id));

        ticketExistant.setTitle(donneesModifiees.getTitle());
        ticketExistant.setDescription(donneesModifiees.getDescription());
        ticketExistant.setStatus(donneesModifiees.getStatus());

        return ticketRepository.save(ticketExistant);
    }

    public void supprimerTicket(UUID id) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket introuvable: " + id);
        }
        ticketRepository.deleteById(id);
    }

    public Ticket resoudreTicket(UUID id, String descriptionResolution) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket introuvable: " + id));

        Resolution resolution = new Resolution(null, descriptionResolution, LocalDateTime.now());
        resolutionRepository.save(resolution);

        ticket.setResolution(resolution);
        ticket.setStatus("RESOLU");
        ticket.setResolvedAt(LocalDateTime.now());

        Ticket ticketResolu = ticketRepository.save(ticket);

        TicketResolvedEvent event = new TicketResolvedEvent(
                ticketResolu.getId(),
                ticketResolu.getCustomerId(),
                descriptionResolution,
                ticketResolu.getResolvedAt()
        );
        ticketEventProducer.publierTicketResolu(event);

        return ticketResolu;
    }

    public void marquerAgentAssigne(AgentAssignedEvent event) {
        ticketRepository.findById(event.ticketId()).ifPresentOrElse(
                ticket -> {
                    ticket.setAgentId(event.agentId());
                    ticket.setStatus("EN_COURS");
                    ticketRepository.save(ticket);
                },
                () -> System.err.println("Ticket introuvable pour l'evenement agent-assigned, ticketId=" + event.ticketId())
        );
    }
}