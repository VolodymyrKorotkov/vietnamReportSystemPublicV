package com.korotkov.creditCRM.model.forRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DatesRequestAndTwoIntegers {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateTo;

    Integer integer1;
    Integer integer2;
}
