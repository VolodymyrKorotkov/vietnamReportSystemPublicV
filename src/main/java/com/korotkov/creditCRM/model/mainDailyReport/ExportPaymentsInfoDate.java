package com.korotkov.creditCRM.model.mainDailyReport;

import java.time.LocalDate;

public class ExportPaymentsInfoDate {
    private LocalDate date;
    private Double paidTotal;
    private Double paidPrincipal;
    private Double paidIncome;
    private Double paidTotalNew;
    private Double paidPrincipalNew;
    private Double paidIncomeNew;
    private Double paidTotalRepeat;
    private Double paidPrincipalRepeat;
    private Double paidIncomeRepeat;

    public ExportPaymentsInfoDate() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(Double paid_for_date) {
        this.paidTotal = paid_for_date;
    }

    public Double getPaidPrincipal() {
        return paidPrincipal;
    }

    public void setPaidPrincipal(Double paid_principal_for_date) {
        this.paidPrincipal = paid_principal_for_date;
    }

    public Double getPaidIncome() {
        return paidIncome;
    }

    public void setPaidIncome(Double paid_income_for_date) {
        this.paidIncome = paid_income_for_date;
    }

    public Double getPaidTotalNew() {
        return paidTotalNew;
    }

    public void setPaidTotalNew(Double paid_for_date_new) {
        this.paidTotalNew = paid_for_date_new;
    }

    public Double getPaidPrincipalNew() {
        return paidPrincipalNew;
    }

    public void setPaidPrincipalNew(Double paid_principal_for_date_new) {
        this.paidPrincipalNew = paid_principal_for_date_new;
    }

    public Double getPaidIncomeNew() {
        return paidIncomeNew;
    }

    public void setPaidIncomeNew(Double paid_income_for_date_new) {
        this.paidIncomeNew = paid_income_for_date_new;
    }

    public Double getPaidTotalRepeat() {
        return paidTotalRepeat;
    }

    public void setPaidTotalRepeat(Double paid_for_date_repeat) {
        this.paidTotalRepeat = paid_for_date_repeat;
    }

    public Double getPaidPrincipalRepeat() {
        return paidPrincipalRepeat;
    }

    public void setPaidPrincipalRepeat(Double paid_principal_for_date_repeat) {
        this.paidPrincipalRepeat = paid_principal_for_date_repeat;
    }

    public Double getPaidIncomeRepeat() {
        return paidIncomeRepeat;
    }

    public void setPaidIncomeRepeat(Double paid_income_for_date_repeat) {
        this.paidIncomeRepeat = paid_income_for_date_repeat;
    }
}
