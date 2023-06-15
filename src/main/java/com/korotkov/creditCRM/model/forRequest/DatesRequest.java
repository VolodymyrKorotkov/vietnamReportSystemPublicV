package com.korotkov.creditCRM.model.forRequest;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DatesRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;

    public DatesRequest() {}

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
