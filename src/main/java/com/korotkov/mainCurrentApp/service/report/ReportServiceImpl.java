package com.korotkov.mainCurrentApp.service.report;

import com.korotkov.mainCurrentApp.dao.report.ReportDao;
import com.korotkov.mainCurrentApp.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    private ReportDao reportDao;

    @Autowired
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(Report report) {
        reportDao.create(report);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(Report report) {
        reportDao.update(report);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Report getById(Long id) {
        return reportDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllReports() {
        return reportDao.getCountAllReports();
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountReportsWithFilterName(String reportName) {
        return reportDao.getCountReportsWithFilterName(reportName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<Report> getAllReports(int page) {
        return reportDao.getAllReports(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<Report> getReportsWithFilterName(int page, String reportName) {
        return reportDao.getReportsWithFilterName(page, reportName);
    }

}
