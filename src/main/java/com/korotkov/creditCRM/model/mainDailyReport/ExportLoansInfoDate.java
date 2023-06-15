package com.korotkov.creditCRM.model.mainDailyReport;

import java.time.LocalDate;

public class ExportLoansInfoDate {
    private LocalDate date;
    private Long countLoan;
    private Double amountLoan;
    private Long countLoanNew;
    private Double amountLoanNew;
    private Long countLoanRepeat;
    private Double amountLoanRepeat;

    public ExportLoansInfoDate() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCountLoan() {
        return countLoan;
    }

    public void setCountLoan(Long count_loan) {
        this.countLoan = count_loan;
    }

    public Double getAmountLoan() {
        return amountLoan;
    }

    public void setAmountLoan(Double amount_loan) {
        this.amountLoan = amount_loan;
    }

    public Long getCountLoanNew() {
        return countLoanNew;
    }

    public void setCountLoanNew(Long count_loan_new) {
        this.countLoanNew = count_loan_new;
    }

    public Double getAmountLoanNew() {
        return amountLoanNew;
    }

    public void setAmountLoanNew(Double amount_loan_new) {
        this.amountLoanNew = amount_loan_new;
    }

    public Long getCountLoanRepeat() {
        return countLoanRepeat;
    }

    public void setCountLoanRepeat(Long count_loan_repeat) {
        this.countLoanRepeat = count_loan_repeat;
    }

    public Double getAmountLoanRepeat() {
        return amountLoanRepeat;
    }

    public void setAmountLoanRepeat(Double amount_loan_repeat) {
        this.amountLoanRepeat = amount_loan_repeat;
    }
}
