package com.korotkov.mainCurrentApp.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "model_plans")
public class ModelPlan {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_month")
    LocalDate dateMonth;

    @Column(name = "contracts_amount_total")
    Double contractsAmountTotal;

    @Column(name = "issued_amount_total")
    Double issuedAmountTotal;

    @Column(name = "issued_amount_repeat")
    Double issuedAmountRepeat;

    @Column(name = "issued_amount_new")
    Double issuedAmountNew;

    @Column(name = "prolonged_amount")
    Double prolongedAmount;

    @Column(name = "repayment_principal_amount_total")
    Double repaymentPrincipalAmountTotal;

    @Column(name = "repayment_principal_amount_new")
    Double repaymentPrincipalAmountNew;

    @Column(name = "repayment_principal_amount_repeat")
    Double repaymentPrincipalAmountRepeat;

    @Column(name = "repayment_income_amount_total")
    Double repaymentIncomeAmountTotal;

    @Column(name = "repayment_income_amount_new")
    Double repaymentIncomeAmountNew;

    @Column(name = "repayment_income_amount_repeat")
    Double repaymentIncomeAmountRepeat;

    @Column(name = "repayment_total_amount_total")
    Double repaymentTotalAmountTotal;

    @Column(name = "repayment_total_amount_new")
    Double repaymentTotalAmountNew;

    @Column(name = "repayment_total_amount_repeat")
    Double repaymentTotalAmountRepeat;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "last_modified_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount lastModifiedBy;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount createdBy;

    public ModelPlan() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Double getContractsAmountTotal() {
        return contractsAmountTotal;
    }

    public Double getIssuedAmountNew() {
        return issuedAmountNew;
    }

    public Double getIssuedAmountRepeat() {
        return issuedAmountRepeat;
    }

    public Double getIssuedAmountTotal() {
        return issuedAmountTotal;
    }

    public Double getProlongedAmount() {
        return prolongedAmount;
    }

    public Double getRepaymentPrincipalAmountTotal() {
        return repaymentPrincipalAmountTotal;
    }

    public Double getRepaymentIncomeAmountNew() {
        return repaymentIncomeAmountNew;
    }

    public Double getRepaymentIncomeAmountRepeat() {
        return repaymentIncomeAmountRepeat;
    }

    public Double getRepaymentIncomeAmountTotal() {
        return repaymentIncomeAmountTotal;
    }

    public Double getRepaymentPrincipalAmountRepeat() {
        return repaymentPrincipalAmountRepeat;
    }

    public void setIssuedAmountNew(Double issuedAmountNew) {
        this.issuedAmountNew = issuedAmountNew;
    }

    public LocalDate getDateMonth() {
        return dateMonth;
    }

    public Double getRepaymentPrincipalAmountNew() {
        return repaymentPrincipalAmountNew;
    }

    public Double getRepaymentTotalAmountNew() {
        return repaymentTotalAmountNew;
    }

    public Double getRepaymentTotalAmountRepeat() {
        return repaymentTotalAmountRepeat;
    }

    public Double getRepaymentTotalAmountTotal() {
        return repaymentTotalAmountTotal;
    }

    public void setContractsAmountTotal(Double contractsAmountTotal) {
        this.contractsAmountTotal = contractsAmountTotal;
    }

    public void setDateMonth(LocalDate dateMonth) {
        this.dateMonth = dateMonth;
    }

    public void setIssuedAmountRepeat(Double issuedAmountRepeat) {
        this.issuedAmountRepeat = issuedAmountRepeat;
    }

    public void setIssuedAmountTotal(Double issuedAmountTotal) {
        this.issuedAmountTotal = issuedAmountTotal;
    }

    public void setProlongedAmount(Double prolongedAmount) {
        this.prolongedAmount = prolongedAmount;
    }

    public void setRepaymentIncomeAmountNew(Double repaymentIncomeAmountNew) {
        this.repaymentIncomeAmountNew = repaymentIncomeAmountNew;
    }

    public void setRepaymentIncomeAmountRepeat(Double repaymentIncomeAmountRepeat) {
        this.repaymentIncomeAmountRepeat = repaymentIncomeAmountRepeat;
    }

    public void setRepaymentIncomeAmountTotal(Double repaymentIncomeAmountTotal) {
        this.repaymentIncomeAmountTotal = repaymentIncomeAmountTotal;
    }

    public void setRepaymentPrincipalAmountNew(Double repaymentPrincipalAmountNew) {
        this.repaymentPrincipalAmountNew = repaymentPrincipalAmountNew;
    }

    public void setRepaymentPrincipalAmountRepeat(Double repaymentPrincipalAmountRepeat) {
        this.repaymentPrincipalAmountRepeat = repaymentPrincipalAmountRepeat;
    }

    public void setRepaymentPrincipalAmountTotal(Double repaymentPrincipalAmountTotal) {
        this.repaymentPrincipalAmountTotal = repaymentPrincipalAmountTotal;
    }

    public void setRepaymentTotalAmountNew(Double repaymentTotalAmountNew) {
        this.repaymentTotalAmountNew = repaymentTotalAmountNew;
    }

    public void setRepaymentTotalAmountRepeat(Double repaymentTotalAmountRepeat) {
        this.repaymentTotalAmountRepeat = repaymentTotalAmountRepeat;
    }

    public void setRepaymentTotalAmountTotal(Double repaymentTotalAmountTotal) {
        this.repaymentTotalAmountTotal = repaymentTotalAmountTotal;
    }

    public UserAccount getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UserAccount lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setCreatedBy(UserAccount createdBy) {
        this.createdBy = createdBy;
    }

    public UserAccount getCreatedBy() {
        return createdBy;
    }
}
