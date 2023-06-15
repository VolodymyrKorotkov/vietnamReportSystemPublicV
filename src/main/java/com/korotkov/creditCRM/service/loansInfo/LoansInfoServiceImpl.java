package com.korotkov.creditCRM.service.loansInfo;

import com.korotkov.creditCRM.dao.loansInfo.LoansInfoDao;
import com.korotkov.creditCRM.model.loansInfo.LoanInfoExpiredInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoansInfoServiceImpl implements LoansInfoService {
    private LoansInfoDao loansInfoDao;

    @Autowired
    public void setLoansInfoDao(LoansInfoDao loansInfoDao) {
        this.loansInfoDao = loansInfoDao;
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<LoanInfoExpiredInfo> getLoanInfoExpiredInfoList (LocalDate dateFrom, LocalDate dateTo) {
        return loansInfoDao.getLoanInfoExpiredInfoList(dateFrom, dateTo);
    }
}
