package com.emsi.sav.serviceclients.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "satisfaction_surveys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatisfactionSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private UUID ticketId;

    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    private LocalDateTime respondedAt;
}