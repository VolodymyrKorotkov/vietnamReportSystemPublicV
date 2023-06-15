package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportDateHourCountAmount {
    private LocalDate date;
    private Integer hour;
    private Long countTotal;
    private Double amountTotal;
    private Long countNew;
    private Double amountNew;
    private Long countRepeat;
    private Double amountRepeat;
}
