package com.emsi.sav.serviceagents.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "workloads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workload {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "agent_id", unique = true)
    private Agent agent;

    @Column(nullable = false)
    private Integer currentLoad = 0; // un agent demarre avec 0 ticket en cours

    @Column(nullable = false)
    private Integer maxCapacity = 5;// et un agent peut gerer jusqu'à 5 tickets pour etre considéré occupé
}