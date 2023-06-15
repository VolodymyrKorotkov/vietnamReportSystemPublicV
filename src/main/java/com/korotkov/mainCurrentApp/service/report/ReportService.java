package com.korotkov.mainCurrentApp.service.report;

import com.korotkov.mainCurrentApp.model.Report;

import java.util.List;

public interface ReportService {
    void create(Report report);
    void update(Report report);
    Report getById(Long id);
    Long getCountAllReports();
    Long getCountReportsWithFilterName(String reportName);
    List<Report> getAllReports(int page);
    List<Report> getReportsWithFilterName(int page, String reportName);
}
