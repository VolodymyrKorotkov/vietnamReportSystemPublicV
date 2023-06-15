package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications_queue_checking")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationsQueueChecking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "date_time")
    LocalDateTime dateTime;

    @Column(name = "count")
    Integer count;
}
