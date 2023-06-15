package com.korotkov.mainCurrentApp.config;

import com.korotkov.mainCurrentApp.config.springBatch.expiredLoans.*;
import com.korotkov.mainCurrentApp.config.springBatch.passiveClient.PassiveClientReader;
import com.korotkov.mainCurrentApp.config.springBatch.passiveClient.PassiveClientWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private JobBuilderFactory jobs;
    private StepBuilderFactory steps;
    private PassiveClientReader passiveClientReader;
//    private PassiveClientProcessor passiveClientProcessor;
    private PassiveClientWriter passiveClientWriter;
    private ExpiredLoansClientReader expiredLoansClientReader;
    private ExpiredLoansClientWriter expiredLoansClientWriter;
    private ExpiredLoansForIVRReader expiredLoansForIVRReader;
    private ExpiredLoansForIVRWriter expiredLoansForIVRWriter;
    private ExpiredLoans3PersonForIVRReader expiredLoans3PersonForIVRReader;
    private ExpiredLoans3PersonForIVRWriter expiredLoans3PersonForIVRWriter;

    @Autowired
    public void setExpiredLoans3PersonForIVRReader(ExpiredLoans3PersonForIVRReader expiredLoans3PersonForIVRReader) {
        this.expiredLoans3PersonForIVRReader = expiredLoans3PersonForIVRReader;
    }

    @Autowired
    public void setExpiredLoans3PersonForIVRWriter(ExpiredLoans3PersonForIVRWriter expiredLoans3PersonForIVRWriter) {
        this.expiredLoans3PersonForIVRWriter = expiredLoans3PersonForIVRWriter;
    }

    @Autowired
    public void setExpiredLoansForIVRReader(ExpiredLoansForIVRReader expiredLoansForIVRReader) {
        this.expiredLoansForIVRReader = expiredLoansForIVRReader;
    }

    @Autowired
    public void setExpiredLoansForIVRWriter(ExpiredLoansForIVRWriter expiredLoansForIVRWriter) {
        this.expiredLoansForIVRWriter = expiredLoansForIVRWriter;
    }

    @Autowired
    public void setExpiredLoansClientReader(ExpiredLoansClientReader expiredLoansClientReader) {
        this.expiredLoansClientReader = expiredLoansClientReader;
    }

    @Autowired
    public void setExpiredLoansClientWriter(ExpiredLoansClientWriter expiredLoansClientWriter) {
        this.expiredLoansClientWriter = expiredLoansClientWriter;
    }

    @Autowired
    public void setPassiveClientWriter(PassiveClientWriter passiveClientWriter) {
        this.passiveClientWriter = passiveClientWriter;
    }

//    @Autowired
//    public void setPassiveClientProcessor(PassiveClientProcessor passiveClientProcessor) {
//        this.passiveClientProcessor = passiveClientProcessor;
//    }

    @Autowired
    public void setPassiveClientReader(PassiveClientReader passiveClientReader) {
        this.passiveClientReader = passiveClientReader;
    }

    @Autowired
    public void setJobs (JobBuilderFactory jobs) {
        this.jobs = jobs;
    }

    @Autowired
    public void setSteps (StepBuilderFactory steps) {
        this.steps = steps;
    }

    @Bean(name = "expiredDebtsForIVR3PersonStep")
    public Step stepExpiredDebtsForIVR3Person() {
        return steps.get("step")
                .chunk(300)
                .reader(expiredLoans3PersonForIVRReader)
                .writer(expiredLoans3PersonForIVRWriter)
                .throttleLimit(20)
                .build();
    }

    @Bean(name = "expiredDebtsForIVR3PersonJob")
    public Job jobExpiredDebtsForIVR3Person() {
        return jobs.get("VicidialApiJobExpiredDebtsForIVR3Person")
                .start(stepExpiredDebtsForIVR3Person())
                .build();
    }


    @Bean(name = "expiredDebtsForIVRStep")
    public Step stepExpiredDebtsForIVR() {
        return steps.get("step")
                .chunk(300)
                .reader(expiredLoansForIVRReader)
                .writer(expiredLoansForIVRWriter)
                .throttleLimit(20)
                .build();
    }

    @Bean(name = "expiredDebtsForIVRJob")
    public Job jobExpiredDebtsForIVR() {
        return jobs.get("VicidialApiJobExpiredDebtsForIVR")
                .start(stepExpiredDebtsForIVR())
                .build();
    }


    @Bean(name = "expiredDebtsForAgentStep")
    public Step stepExpiredDebtsForAgent() {
        return steps.get("step")
                .chunk(300)
                .reader(expiredLoansClientReader)
                .writer(expiredLoansClientWriter)
                .throttleLimit(20)
                .build();
    }

    @Bean(name = "expiredDebtsForAgentJob")
    public Job jobExpiredDebtsForAgent() {
        return jobs.get("VicidialApiJobExpiredDebtsForAgent")
                .start(stepExpiredDebtsForAgent())
                .build();
    }


    @Bean(name = "passiveClientsStep")
    public Step stepPassiveClients() {
        return steps.get("step")
                .chunk(300)
                .reader(passiveClientReader)
//                .processor(passiveClientProcessor)
                .writer(passiveClientWriter)
                .throttleLimit(20)
                .build();
    }

    @Bean(name = "passiveClientsJob")
    public Job jobPassiveClients() {
        return jobs.get("VicidialApiJobPassiveClients")
                .start(stepPassiveClients())
                .build();
    }


}
