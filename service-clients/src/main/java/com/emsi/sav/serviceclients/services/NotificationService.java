package com.emsi.sav.serviceclients.services;

import com.emsi.sav.serviceclients.entities.Customer;
import com.emsi.sav.serviceclients.entities.History;
import com.emsi.sav.serviceclients.entities.SatisfactionSurvey;
import com.emsi.sav.serviceclients.events.TicketCreatedEvent;
import com.emsi.sav.serviceclients.events.TicketResolvedEvent;
import com.emsi.sav.serviceclients.repositories.CustomerRepository;
import com.emsi.sav.serviceclients.repositories.HistoryRepository;
import com.emsi.sav.serviceclients.repositories.SatisfactionSurveyRepository;
import com.emsi.sav.serviceclients.strategies.NotificationStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationService {

    private final CustomerRepository customerRepository;
    private final HistoryRepository historyRepository;
    private final NotificationStrategy notificationStrategy;
    private final SatisfactionSurveyRepository satisfactionSurveyRepository;

    public NotificationService(CustomerRepository customerRepository, HistoryRepository historyRepository, NotificationStrategy notificationStrategy, SatisfactionSurveyRepository satisfactionSurveyRepository) {
        this.customerRepository = customerRepository;
        this.historyRepository = historyRepository;
        this.notificationStrategy = notificationStrategy;
        this.satisfactionSurveyRepository = satisfactionSurveyRepository;
    }

    public void notifierCreationTicket(TicketCreatedEvent event) {
        if (historyRepository.existsByTicketIdAndDescriptionStartingWith(event.ticketId(), "Ticket cree")) {
            return;
        }

        Customer customer = trouverOuCreerCustomer(event.customerId());

        String message = "Bonjour,\n\nVotre ticket \"" + event.title() + "\" a bien ete enregistre.\n"
                + "Priorite : " + event.priorityLabel() + "\n"
                + "Categorie : " + event.categoryName() + "\n\n"
                + "Notre equipe va le traiter dans les meilleurs delais.";

        notificationStrategy.send(customer.getEmail(), "Confirmation de votre ticket", message);

        History history = new History(null, customer, event.ticketId(),
                "Ticket cree : " + event.title(), LocalDateTime.now());
        historyRepository.save(history);
    }

    public void notifierResolutionTicket(TicketResolvedEvent event) {
        if (historyRepository.existsByTicketIdAndDescriptionStartingWith(event.ticketId(), "Ticket resolu")) {
            return;
        }
        Customer customer = trouverOuCreerCustomer(event.customerId());

        String message = "Bonjour,\n\nVotre ticket a ete resolu.\n\n"
                + "Details de la resolution : " + event.resolutionDescription() + "\n\n"
                + "Nous vous invitons a repondre a notre enquete de satisfaction pour nous aider a nous ameliorer.";

        notificationStrategy.send(customer.getEmail(), "Votre ticket a ete resolu", message);

        History history = new History(null, customer, event.ticketId(),
                "Ticket resolu", LocalDateTime.now());
        historyRepository.save(history);

        SatisfactionSurvey survey = new SatisfactionSurvey(
                null, customer, event.ticketId(), null, null, LocalDateTime.now(), null
        );
        satisfactionSurveyRepository.save(survey);
    }


    private Customer trouverOuCreerCustomer(UUID customerId){
        if (customerId == null){
            return customerRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("Aucun client de test disponible"));
        }
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable : " + customerId));
    }
}
