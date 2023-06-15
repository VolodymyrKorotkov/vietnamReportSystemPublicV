package com.korotkov.creditCRM.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CheckerEntity {
    Long id;
    LocalDateTime dateTime;
}
