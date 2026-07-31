package com.emsi.sav.servicetickets.strategies;

import com.emsi.sav.servicetickets.entities.Category;
import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KeywordBasedCategoryStrategy implements CategoryStrategy {

    private static final List<String> MOTS_CLES_TECHNIQUE = List.of("bug", "panne", "serveur", "connexion", "compte bloque", "acces impossible", "site down");
    private static final List<String> MOTS_CLES_FACTURATION = List.of("facture", "paiement", "prelevement", "montant", "remboursement", "abonnement");
    private static final List<String> MOTS_CLES_RECLAMATION = List.of("reclamation", "insatisfait", "mecontent", "defectueux", "deception");

    private final CategoryRepository categoryRepository;

    public KeywordBasedCategoryStrategy(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category calculerCategorie(Ticket ticket) {
        String contenu = (ticket.getTitle() + " " + ticket.getDescription()).toLowerCase();

        Map<String, Long> scores = Map.of(
                "TECHNIQUE", compterOccurrences(contenu, MOTS_CLES_TECHNIQUE),
                "FACTURATION", compterOccurrences(contenu, MOTS_CLES_FACTURATION),
                "RECLAMATION", compterOccurrences(contenu, MOTS_CLES_RECLAMATION)
        );

        String name = scores.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("GENERALE");

        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Categorie introuvable: " + name));
    }

    private long compterOccurrences(String contenu, List<String> motsCles) {
        return motsCles.stream().filter(contenu::contains).count();
    }
}