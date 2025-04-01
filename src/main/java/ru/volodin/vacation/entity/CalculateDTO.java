package ru.volodin.vacation.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalculateDTO {
    private LocalDate dayStart;
    private LocalDate dayFinish;
    private float salary;
}
