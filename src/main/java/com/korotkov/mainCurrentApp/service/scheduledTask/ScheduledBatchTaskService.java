package com.korotkov.mainCurrentApp.service.scheduledTask;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScheduledBatchTaskService {
    private Job jobVicidialPassiveClients;
    private Job jobExpiredDebtsForIVR;
    private Job jobExpiredDebtsForAgent;
    private Job jobExpiredDebtsForIVR3Person;
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("expiredDebtsForIVRJob")
    public void setJobExpiredDebtsForIVR(Job jobExpiredDebtsForIVR) {
        this.jobExpiredDebtsForIVR = jobExpiredDebtsForIVR;
    }

    @Autowired
    @Qualifier("expiredDebtsForIVR3PersonJob")
    public void setJobExpiredDebtsForIVR3Person(Job jobExpiredDebtsForIVR3Person) {
        this.jobExpiredDebtsForIVR3Person = jobExpiredDebtsForIVR3Person;
    }

    @Autowired
    @Qualifier("expiredDebtsForAgentJob")
    public void setJobExpiredDebtsForAgent(Job jobExpiredDebtsForAgent) {
        this.jobExpiredDebtsForAgent = jobExpiredDebtsForAgent;
    }

    @Autowired
    @Qualifier("passiveClientsJob")
    public void setJobVicidialPassiveClients(Job jobVicidialPassiveClients) {
        this.jobVicidialPassiveClients = jobVicidialPassiveClients;
    }

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }


//    @Async("batchExecutorForVicidial")
//    @Scheduled(cron = "0 55 9 * * *", zone = TIME_ZONE)
//    public void startJobVicidialPassiveClients() throws Exception {
//        jobLauncher.run(jobVicidialPassiveClients, new JobParametersBuilder().addLong("uniqueness",
//                System.nanoTime()).toJobParameters());
//    }

//    @Async("batchExecutorForVicidial")
//    @Scheduled(cron = "0 45 6 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 30 7 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 30 12 * * *", zone = TIME_ZONE)
//    public void startJobVicidialExpiredDebtsForAgent() throws Exception {
//        jobLauncher.run(jobExpiredDebtsForAgent, new JobParametersBuilder().addLong("uniqueness",
//                System.nanoTime()).toJobParameters());
//    }

//    @Async("batchExecutorForVicidial")
//    @Scheduled(cron = "0 11 10 * * *", zone = TIME_ZONE)
//    public void startJobVicidialExpiredDebts3PersonForIVR() throws Exception {
//        jobLauncher.run(jobExpiredDebtsForIVR3Person, new JobParametersBuilder().addLong("uniqueness",
//                System.nanoTime()).toJobParameters());
//    }
//
//    @Async("batchExecutorForVicidial")
//    @Scheduled(cron = "0 55 6 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 17 7 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 35 7 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 0 8 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 30 8 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 55 8 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 20 9 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 5 10 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 25 10 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 55 11 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 45 12 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 5 16 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 35 16 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 15 17 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 40 17 * * *", zone = TIME_ZONE)
//    @Scheduled(cron = "0 40 19 * * *", zone = TIME_ZONE)
//    public void startJobVicidialExpiredDebtsForIVR1() throws Exception {
//        jobLauncher.run(jobExpiredDebtsForIVR, new JobParametersBuilder().addLong("uniqueness",
//                System.nanoTime()).toJobParameters());
//    }


}
