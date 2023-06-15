package com.korotkov.creditCRM.model.payments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportPaymentWithPaidFees {
    LocalDate date;
    Long loanId;
    Long appId;
    BigDecimal balance;
    String loanStatus;
    String brokerName;
    String pawnshopName;
    String documentNumber;
    String clientName;
    String email;
    String loanAgreement;
    String consultingAgreement;
    BigDecimal paidForDate;
    BigDecimal paidProlongationForDate;
    BigDecimal paidConsultingForDate;
    BigDecimal paidCollectionFeeForDate;
    BigDecimal paidInterestForDate;
    BigDecimal paidPenaltyInterestForDate;
    BigDecimal paidPrincipalForDate;

    BigDecimal paidTotalIncomeBroker;
    BigDecimal revenueBroker;
    BigDecimal outgoingVATBroker;
    BigDecimal paidTotalIncomePawnShop;
    BigDecimal revenuePawnShop;
    BigDecimal outgoingVATPawnShop;

    boolean totalRow;
}
