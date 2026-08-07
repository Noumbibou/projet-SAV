package com.emsi.sav.servicetickets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket_sla_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSlaResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID ticketId;

    @Column(nullable = false)
    private String priorityLabel;

    @Column(nullable = false)
    private double durationHours;

    @Column(nullable = false)
    private long slaThresholdHours;

    @Column(nullable = false)
    private boolean respected;

    @Column(nullable = false)
    private LocalDateTime evaluatedAt;
}