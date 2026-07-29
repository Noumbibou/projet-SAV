package com.emsi.sav.servicetickets.repositories;

import com.emsi.sav.servicetickets.entities.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InterfaceAddress;
import java.util.UUID;

public interface ResolutionRepository extends JpaRepository<Resolution, UUID> {
}
