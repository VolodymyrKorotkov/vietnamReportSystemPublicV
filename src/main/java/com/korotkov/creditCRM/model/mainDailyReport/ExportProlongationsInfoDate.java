package com.korotkov.creditCRM.model.mainDailyReport;

import java.time.LocalDate;

public class ExportProlongationsInfoDate {
    private LocalDate date;
    private Long countProlonged;
    private Double principalAmountProlonged;
    private Long countProlongedNew;
    private Double principalAmountProlongedNew;
    private Long countProlongedRepeat;
    private Double principalAmountProlongedRepeat;

    public ExportProlongationsInfoDate() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCountProlongedNew() {
        return countProlongedNew;
    }

    public void setCountProlongedNew(Long count_prolong_new) {
        this.countProlongedNew = count_prolong_new;
    }

    public Double getPrincipalAmountProlongedNew() {
        return principalAmountProlongedNew;
    }

    public void setPrincipalAmountProlongedNew(Double principal_prolong_new) {
        this.principalAmountProlongedNew = principal_prolong_new;
    }

    public Long getCountProlonged() {
        return countProlonged;
    }

    public void setCountProlonged(Long count_prolong) {
        this.countProlonged = count_prolong;
    }

    public Double getPrincipalAmountProlonged() {
        return principalAmountProlonged;
    }

    public void setPrincipalAmountProlonged(Double principal_prolong) {
        this.principalAmountProlonged = principal_prolong;
    }

    public Long getCountProlongedRepeat() {
        return countProlongedRepeat;
    }

    public void setCountProlongedRepeat(Long count_prolong_repeat) {
        this.countProlongedRepeat = count_prolong_repeat;
    }

    public Double getPrincipalAmountProlongedRepeat() {
        return principalAmountProlongedRepeat;
    }

    public void setPrincipalAmountProlongedRepeat(Double principal_prolong_repeat) {
        this.principalAmountProlongedRepeat = principal_prolong_repeat;
    }
}
