package com.korotkov.mainCurrentApp.service.uploadingClientPhones;

import com.korotkov.mainCurrentApp.dao.uploadingClientPhones.UploadingClientPhonesDAO;
import com.korotkov.mainCurrentApp.model.UploadingClientPhones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UploadingClientPhonesServiceImpl implements UploadingClientPhonesService {
    private UploadingClientPhonesDAO uploadingClientPhonesDAO;

    @Autowired
    public void setUploadingClientPhonesDAO(UploadingClientPhonesDAO uploadingClientPhonesDAO) {
        this.uploadingClientPhonesDAO = uploadingClientPhonesDAO;
    }


    @Override
    @Transactional("transactionManagerMain")
    public void create(UploadingClientPhones uploadingClientPhones) {
        uploadingClientPhonesDAO.create(uploadingClientPhones);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(UploadingClientPhones uploadingClientPhones) {
        uploadingClientPhonesDAO.update(uploadingClientPhones);
    }

    @Override
    @Transactional("transactionManagerMain")
    public UploadingClientPhones getById(Long id) {
        return uploadingClientPhonesDAO.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllUploadingClientPhones() {
        return uploadingClientPhonesDAO.getCountAllUploadingClientPhones();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UploadingClientPhones> getAllUploadingClientPhones(int page) {
        return uploadingClientPhonesDAO.getAllUploadingClientPhones(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo) {
        return uploadingClientPhonesDAO.getCountUploadingClientPhonesForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UploadingClientPhones> getUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo, int page)  {
        return uploadingClientPhonesDAO.getUploadingClientPhonesForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUploadingClientPhonesForDateFrom(LocalDate dateFrom) {
        return uploadingClientPhonesDAO.getCountUploadingClientPhonesForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UploadingClientPhones> getUploadingClientPhonesForDateFrom(LocalDate dateFrom, int page) {
        return uploadingClientPhonesDAO.getUploadingClientPhonesForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUploadingClientPhonesForDateTo(LocalDate dateTo) {
        return uploadingClientPhonesDAO.getCountUploadingClientPhonesForDateTo(dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UploadingClientPhones> getUploadingClientPhonesForDateTo(LocalDate dateTo, int page) {
        return uploadingClientPhonesDAO.getUploadingClientPhonesForDateTo(dateTo, page);
    }

}
