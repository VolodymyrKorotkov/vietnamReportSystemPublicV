package com.korotkov.creditCRM.dao.loansInfo;

import com.korotkov.creditCRM.model.loansInfo.LoanInfoExpiredInfo;

import java.time.LocalDate;
import java.util.List;

public interface LoansInfoDao {
    List<LoanInfoExpiredInfo> getLoanInfoExpiredInfoList (LocalDate dateFrom, LocalDate dateTo);
}
