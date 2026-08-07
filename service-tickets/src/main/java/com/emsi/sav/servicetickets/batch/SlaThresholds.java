package com.emsi.sav.servicetickets.batch;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SlaThresholds {

    private static final Map<String, Long> SEUILS_HEURES = Map.of(
            "CRITIQUE", 2L,
            "HAUTE", 8L,
            "MOYENNE", 24L,
            "BASSE", 72L
    );

    public long getSeuilHeures(String priorityLabel) {
        return SEUILS_HEURES.getOrDefault(priorityLabel, 24L);
    }
}