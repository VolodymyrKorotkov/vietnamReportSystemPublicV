package com.korotkov.creditCRM.service.loansInfo;

import com.korotkov.creditCRM.model.loansInfo.LoanInfoExpiredInfo;

import java.time.LocalDate;
import java.util.List;

public interface LoansInfoService {
    List<LoanInfoExpiredInfo> getLoanInfoExpiredInfoList (LocalDate dateFrom, LocalDate dateTo);
}
