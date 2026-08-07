package com.emsi.sav.serviceagents.config;

import com.emsi.sav.serviceagents.entities.*;
import com.emsi.sav.serviceagents.repositories.AgentRepository;
import com.emsi.sav.serviceagents.repositories.SkillRepository;
import com.emsi.sav.serviceagents.repositories.UserRepository;
import com.emsi.sav.serviceagents.repositories.WorkloadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AgentRepository agentRepository;
    private final SkillRepository skillRepository;
    private final WorkloadRepository workloadRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, AgentRepository agentRepository, SkillRepository skillRepository, WorkloadRepository workloadRepository, PasswordEncoder passwordEncoder) {
        this.agentRepository = agentRepository;
        this.skillRepository = skillRepository;
        this.workloadRepository = workloadRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (agentRepository.count() == 0) {
            Agent alice = agentRepository.save(new Agent(null, "Alice Bennani", "alice.bennani@sav.ma", null));
            Agent karim = agentRepository.save(new Agent(null, "Karim El Fassi", "karim.elfassi@sav.ma", null));
            Agent sara = agentRepository.save(new Agent(null, "Sara Idrissi", "sara.idrissi@sav.ma", null));

            skillRepository.save(new Skill(null, SkillType.TECHNIQUE, alice));
            skillRepository.save(new Skill(null, SkillType.RECLAMATION, alice));
            skillRepository.save(new Skill(null, SkillType.FACTURATION, karim));
            skillRepository.save(new Skill(null, SkillType.GENERALE, karim));
            skillRepository.save(new Skill(null, SkillType.TECHNIQUE, sara));
            skillRepository.save(new Skill(null, SkillType.FACTURATION, sara));

            workloadRepository.save(new Workload(null, alice, 0, 5));
            workloadRepository.save(new Workload(null, karim, 0, 5));
            workloadRepository.save(new Workload(null, sara, 0, 5));
        }

        if (userRepository.count() == 0) {
            Agent aliceRef = agentRepository.findAll().stream()
                    .filter(a -> "alice.bennani@sav.ma".equals(a.getEmail()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Agent Alice introuvable pour le seeding des users"));

            userRepository.save(new User(null, "responsable@sav.ma",
                    passwordEncoder.encode("password123"), Role.RESPONSABLE, null));
            userRepository.save(new User(null, "alice.bennani@sav.ma",
                    passwordEncoder.encode("password123"), Role.AGENT, aliceRef.getId()));
        }
    }
}