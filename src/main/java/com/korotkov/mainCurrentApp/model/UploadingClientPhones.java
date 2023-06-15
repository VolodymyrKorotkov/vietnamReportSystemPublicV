package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploading_client_phones")
@Getter
@Setter
@NoArgsConstructor
public class UploadingClientPhones {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "started_at")
    LocalDateTime startedAt;

    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "uploaded_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount uploadedBy;

    @Column(name = "count_phones_for_adding")
    Integer countPhonesForAdding;

    @Column(name = "count_phones_added")
    Integer countPhonesAdded;
}
