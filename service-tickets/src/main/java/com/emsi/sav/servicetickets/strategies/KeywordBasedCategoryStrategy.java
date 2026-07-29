package com.emsi.sav.servicetickets.strategies;

import com.emsi.sav.servicetickets.entities.Category;
import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeywordBasedCategoryStrategy implements CategoryStrategy{

    private static final List<String> MOTS_CLES_TECHNIQUE = List.of("bug", "erreur", "panne", "serveur", "connexion", "compte", "accès", "acces");
    private static final List<String> MOTS_CLES_FACTURATION = List.of("facture", "paiement", "prélèvement", "prelevement", "montant", "remboursement");
    private static final List<String> MOTS_CLES_RECLAMATION = List.of("réclamation", "reclamation", "insatisfait", "mécontent", "mecontent", "défectueux", "defectueux");

    private final CategoryRepository categoryRepository;

    public KeywordBasedCategoryStrategy(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category calculerCategorie(Ticket ticket) {
        String contenu = (ticket.getTitle() + " " + ticket.getDescription()).toLowerCase();

        String name;

        if (contientUnMot(contenu, MOTS_CLES_TECHNIQUE)) {
            name = "TECHNIQUE";
        } else if (contientUnMot(contenu, MOTS_CLES_FACTURATION)) {
            name = "FACTURATION";
        } else if (contientUnMot(contenu, MOTS_CLES_RECLAMATION)) {
            name = "RECLAMATION";
        } else {
            name = "GENERALE";
        }

        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Categorie introuvable: " + name));
    }

    private boolean contientUnMot(String contenu, List<String> motsCles) {
        return motsCles.stream().anyMatch(contenu::contains);
    }
}
