package com.emsi.sav.servicetickets.services;

import com.emsi.sav.servicetickets.entities.Ticket;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {

    private final ChatClient chatClient;

    public SuggestionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String genererSuggestion(Ticket ticket) {
        String prompt = """
                Tu es un assistant de support client. Voici un ticket de support :
                Titre : %s
                Description : %s
                Categorie : %s
                Priorite : %s

                Redige une reponse courte, professionnelle et empathique en francais
                pour aider l'agent a repondre rapidement a ce client. 3 phrases maximum.
                """.formatted(
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCategory() != null ? ticket.getCategory().getName() : "GENERALE",
                ticket.getPriority() != null ? ticket.getPriority().getLabel() : "MOYENNE"
        );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}