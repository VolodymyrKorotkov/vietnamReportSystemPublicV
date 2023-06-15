package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_task")
@Getter
@Setter
@RequiredArgsConstructor
public class ScheduledTask {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "done_at")
    LocalDateTime doneAt;

    @Column(name = "task")
    String task;

    @Column(name = "status")
    String status;

    @Column(name = "comment")
    String comment;
}
