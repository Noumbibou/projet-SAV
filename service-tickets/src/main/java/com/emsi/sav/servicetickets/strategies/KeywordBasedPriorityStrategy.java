package com.emsi.sav.servicetickets.strategies;

import com.emsi.sav.servicetickets.entities.Priority;
import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.repositories.PriorityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeywordBasedPriorityStrategy implements PriorityStrategy{

    private static final List<String> MOT_CLES_CRITQUE = List.of("urgent", "bloqué", "panne", "down", "critique");
    private static final List<String> MOT_CLES_HAUTE = List.of("important", "rapidement", "problème");

    private final PriorityRepository priorityRepository;

    public KeywordBasedPriorityStrategy(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public Priority calculerPriorite(Ticket ticket) {
        String contenu = (ticket.getTitle() + " " + ticket.getDescription()).toLowerCase();
        String label;

        if(contientUnMot(contenu, MOT_CLES_CRITQUE)){
            label= "CRITIQUE";
        } else if (contientUnMot(contenu, MOT_CLES_HAUTE)) {
            label = "HAUTE";
        } else {
            label = "MOYENNE";
        }

        return priorityRepository.findByLabel(label).orElseThrow(() -> new IllegalStateException("Priorite introuvable: " + label));
    }

    private boolean contientUnMot(String contenu, List<String> motCles){
        return motCles.stream().anyMatch(contenu::contains);
    }
}
