package com.emsi.sav.servicetickets.services;

import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.events.TicketCreatedEvent;
import com.emsi.sav.servicetickets.kafka.TicketEventProducer;
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

    public TicketService(TicketRepository ticketRepository, PriorityStrategy priorityStrategy, TicketEventProducer ticketEventProducer, CategoryStrategy categoryStrategy) {
        this.ticketRepository = ticketRepository;
        this.priorityStrategy = priorityStrategy;
        this.ticketEventProducer = ticketEventProducer;
        this.categoryStrategy = categoryStrategy;
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


}