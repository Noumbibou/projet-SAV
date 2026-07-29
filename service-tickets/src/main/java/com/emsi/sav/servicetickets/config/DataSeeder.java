package com.emsi.sav.servicetickets.config;

import com.emsi.sav.servicetickets.entities.Category;
import com.emsi.sav.servicetickets.entities.Priority;
import com.emsi.sav.servicetickets.repositories.CategoryRepository;
import com.emsi.sav.servicetickets.repositories.PriorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PriorityRepository priorityRepository;
    private final CategoryRepository categoryRepository;

    public DataSeeder(PriorityRepository priorityRepository, CategoryRepository categoryRepository) {
        this.priorityRepository = priorityRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (priorityRepository.count() == 0) {
            priorityRepository.save(new Priority(null, 1, "BASSE"));
            priorityRepository.save(new Priority(null, 2, "MOYENNE"));
            priorityRepository.save(new Priority(null, 3, "HAUTE"));
            priorityRepository.save(new Priority(null, 4, "CRITIQUE"));
        }

        if (categoryRepository.count() == 0){
            categoryRepository.save(new Category(null, "TECHNIQUE", "Problemes techniques et pannes"));
            categoryRepository.save(new Category(null, "FACTURATION", "Questions de facturation et paiement"));
            categoryRepository.save(new Category(null, "GENERALE", "Questions generales"));
            categoryRepository.save(new Category(null, "RECLAMATION", "Reclamations produit ou service"));
        }
    }
}
