package com.korotkov.mainCurrentApp.model;

import javax.persistence.*;

@Entity
@Table(name = "report_list")
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "report_name")
    String reportName;

    @Column(name = "scheduled")
    boolean scheduled;

    public Report(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }
}
