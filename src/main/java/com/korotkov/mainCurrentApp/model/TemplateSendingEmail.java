package com.korotkov.mainCurrentApp.model;

import com.korotkov.mainCurrentApp.model.helpedObjects.PeriodDays;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "template_sending_email")
@Getter
@Setter
@NoArgsConstructor
public class TemplateSendingEmail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "subject_email")
    String subjectEmail;

    @Column(name = "body_email_title")
    String bodyEmailTitle;

    @Column(name = "body_email_text")
    String bodyEmailText;

    @Column(name = "button_email_text")
    String buttonEmailText;

    @Column(name = "link_for_click")
    String linkForClick;

    @Column(name = "banner_link")
    String bannerLink;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount createdBy;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "last_modified_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount lastModifiedBy;


//    @Transient
//    boolean allActiveClientsWithoutExpiredLoans;
//
//    @Transient
//    boolean repeatPassiveClients;
//
//    @Transient
//    PeriodDays periodDaysRepeatPassiveClients;
//
//    @Transient
//    boolean clientsWithExpiredLoan;
//
//    @Transient
//    PeriodDays periodDaysClientsWithExpiredLoan;
//
//    @Transient
//    boolean registeredClientsWithoutApplications;
//
//    @Transient
//    PeriodDays periodDaysRegisteredClientsWithoutApplications;

}
