package com.emsi.sav.servicetickets.batch;

import com.emsi.sav.servicetickets.entities.Ticket;
import com.emsi.sav.servicetickets.entities.TicketSlaResult;
import com.emsi.sav.servicetickets.repositories.TicketRepository;
import com.emsi.sav.servicetickets.repositories.TicketSlaResultRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;

@Configuration
public class SlaReportJobConfig {

    @Bean
    public RepositoryItemReader<Ticket> ticketReader(TicketRepository ticketRepository) {
        RepositoryItemReader<Ticket> reader = new RepositoryItemReader<>();
        reader.setRepository(ticketRepository);
        reader.setMethodName("findByStatus");
        reader.setArguments(List.of("RESOLU"));
        reader.setSort(Map.of("resolvedAt", Sort.Direction.ASC));
        reader.setPageSize(10);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<TicketSlaResult> slaResultWriter(TicketSlaResultRepository resultRepository) {
        RepositoryItemWriter<TicketSlaResult> writer = new RepositoryItemWriter<>();
        writer.setRepository(resultRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step slaCalculationStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   RepositoryItemReader<Ticket> ticketReader,
                                   SlaItemProcessor slaItemProcessor,
                                   RepositoryItemWriter<TicketSlaResult> slaResultWriter) {
        return new StepBuilder("slaCalculationStep", jobRepository)
                .<Ticket, TicketSlaResult>chunk(10, transactionManager)
                .reader(ticketReader)
                .processor(slaItemProcessor)
                .writer(slaResultWriter)
                .build();
    }

    @Bean
    public Job slaReportJob(JobRepository jobRepository, Step slaCalculationStep) {
        return new JobBuilder("slaReportJob", jobRepository)
                .start(slaCalculationStep)
                .build();
    }
}