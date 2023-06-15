package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sending_email")
@Getter
@Setter
@NoArgsConstructor
public class SendingEmail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "started_at")
    LocalDateTime startedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "planned_at")
    LocalDateTime plannedAt;

    @Column(name = "completed_at")
    LocalDateTime completedAt;

    @Column(name = "status")
    String status;

    @Column(name = "auto")
    boolean auto;

    @Column(name = "count_emails_total")
    Integer countEmailsTotal;

    @Column(name = "count_emails_sent")
    Integer countEmailsSent;

    @Column(name = "count_emails_not_sent")
    Integer countEmailsNotSent;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "template")
    @ManyToOne(fetch = FetchType.EAGER)
    TemplateSendingEmail templateSendingEmail;

    @Column(name = "active_clients")
    boolean allActiveClientsWithoutExpiredLoans;

    @Column(name = "passive_clients")
    boolean repeatPassiveClients;

    @Column(name = "passive_clients_days_from")
    int repeatPassiveClientsDaysFrom;

    @Column(name = "passive_clients_days_to")
    int repeatPassiveClientsDaysTo;

    @Column(name = "expired_clients")
    boolean clientsWithExpiredLoan;

    @Column(name = "expired_clients_days_from")
    int clientsWithExpiredLoanDaysFrom;

    @Column(name = "expired_clients_days_to")
    int clientsWithExpiredLoanDaysTo;

    @Column(name = "registered_without_apps")
    boolean registeredClientsWithoutApplications;

    @Column(name = "registered_without_apps_days_from")
    int registeredClientsWithoutApplicationsDaysFrom;

    @Column(name = "registered_without_apps_days_to")
    int registeredClientsWithoutApplicationsDaysTo;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount createdBy;

}
