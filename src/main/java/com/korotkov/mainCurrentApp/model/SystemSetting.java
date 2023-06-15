package com.korotkov.mainCurrentApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_settings")
@Getter
@Setter
@NoArgsConstructor
public class SystemSetting {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "value")
    String value;

    @Column(name = "type")
    String type;

    @Column(name = "last_modified_at")
    LocalDateTime modifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "last_modified_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount modifiedBy;
}
