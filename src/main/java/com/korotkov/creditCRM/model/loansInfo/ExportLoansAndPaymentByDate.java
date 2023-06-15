package com.korotkov.creditCRM.model.loansInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportLoansAndPaymentByDate {
    LocalDate date;
    Double amountLoans;
    Double amountPayments;
}
