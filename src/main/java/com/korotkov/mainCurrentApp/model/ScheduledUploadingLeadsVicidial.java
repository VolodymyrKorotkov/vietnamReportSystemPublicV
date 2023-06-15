package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_uploading_leads_vicidial")
@Getter
@Setter
@RequiredArgsConstructor
public class ScheduledUploadingLeadsVicidial {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "started_export_at")
    LocalDateTime startedExportAt;

    @Column(name = "started_uploading_at")
    LocalDateTime startedUploadingAt;

    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    @Column(name = "uploading_name")
    String uploadingName;

    @Column(name = "status")
    String status;

    @Column(name = "uploaded_count")
    Integer uploadedCount;

    @Column(name = "comment")
    String comment;
}
