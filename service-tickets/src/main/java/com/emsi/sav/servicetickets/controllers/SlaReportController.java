package com.emsi.sav.servicetickets.controllers;

import com.emsi.sav.servicetickets.entities.TicketSlaResult;
import com.emsi.sav.servicetickets.repositories.TicketSlaResultRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sla-reports")
public class SlaReportController {

    private final JobLauncher jobLauncher;
    private final Job slaReportJob;
    private final TicketSlaResultRepository resultRepository;

    public SlaReportController(JobLauncher jobLauncher, Job slaReportJob, TicketSlaResultRepository resultRepository) {
        this.jobLauncher = jobLauncher;
        this.slaReportJob = slaReportJob;
        this.resultRepository = resultRepository;
    }

    @PostMapping("/lancer")
    public ResponseEntity<Map<String, String>> lancerManuel() throws Exception {
        JobParameters parametres = new JobParametersBuilder()
                .addLocalDateTime("dateExecution", LocalDateTime.now())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(slaReportJob, parametres);

        Map<String, String> reponse = Map.of(
                "statut", execution.getStatus().toString(),
                "sortie", execution.getExitStatus().getExitCode(),
                "details", execution.getExitStatus().getExitDescription()
        );
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<List<TicketSlaResult>> listerResultats() {
        return ResponseEntity.ok(resultRepository.findAll());
    }

    @GetMapping("/synthese")
    public ResponseEntity<Map<String, Object>> synthese() {
        List<TicketSlaResult> resultats = resultRepository.findAll();
        Map<String, List<TicketSlaResult>> parPriorite = resultats.stream()
                .collect(Collectors.groupingBy(TicketSlaResult::getPriorityLabel));

        Map<String, Object> synthese = new LinkedHashMap<>();
        parPriorite.forEach((priorite, liste) -> {
            long total = liste.size();
            long respectes = liste.stream().filter(TicketSlaResult::isRespected).count();
            double taux = total == 0 ? 0 : Math.round(respectes * 10000.0 / total) / 100.0;
            synthese.put(priorite, Map.of("total", total, "respectes", respectes, "tauxRespectPourcent", taux));
        });
        return ResponseEntity.ok(synthese);
    }
}