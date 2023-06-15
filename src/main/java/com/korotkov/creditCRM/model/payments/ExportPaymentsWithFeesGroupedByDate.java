package com.korotkov.creditCRM.model.payments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExportPaymentsWithFeesGroupedByDate {
    LocalDate date;
    BigDecimal revenueBroker;
    BigDecimal outgoingVATBroker;
    BigDecimal revenuePawnShop;
    BigDecimal outgoingVATPawnShop;
    BigDecimal totalRevenue;
    BigDecimal totalOutgoingVAT;
    BigDecimal paidPrincipal;
    boolean total;
}
