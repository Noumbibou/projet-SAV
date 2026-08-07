package com.emsi.sav.servicetickets.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SlaReportScheduler {

    private final JobLauncher jobLauncher;
    private final Job slaReportJob;

    public SlaReportScheduler(JobLauncher jobLauncher, Job slaReportJob) {
        this.jobLauncher = jobLauncher;
        this.slaReportJob = slaReportJob;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void lancerRapportQuotidien() throws Exception {
        JobParameters parametres = new JobParametersBuilder()
                .addLocalDateTime("dateExecution", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(slaReportJob, parametres);
    }
}