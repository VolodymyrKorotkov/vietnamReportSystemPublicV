package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_email_verification")
@Getter
@Setter
@NoArgsConstructor
public class ClientEmailVerification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "client_id")
    Long clientId;

    @Column(name = "email")
    String email;

    @Column(name = "code")
    String code;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "done")
    boolean done;

    @Column(name = "done_at")
    LocalDateTime doneAt;
}
