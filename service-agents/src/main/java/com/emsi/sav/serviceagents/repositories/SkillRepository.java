package com.emsi.sav.serviceagents.repositories;

import com.emsi.sav.serviceagents.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
    List<Skill> findByName(String name);
}