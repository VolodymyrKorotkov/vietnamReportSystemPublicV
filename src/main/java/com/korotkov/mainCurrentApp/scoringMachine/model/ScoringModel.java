package com.korotkov.mainCurrentApp.scoringMachine.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scoring_model")
public class ScoringModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;

    public ScoringModel(){

    }

    public Long getId(){
        return id;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getLastModifiedAt(){
        return lastModifiedAt;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt){
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


