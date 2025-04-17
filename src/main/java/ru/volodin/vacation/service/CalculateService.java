package ru.volodin.vacation.service;

import java.time.LocalDate;

public interface CalculateService {
    float getVacationCompensationEazy(float salary, int dayCount);
    float getVacationCompensationHard(float salary, LocalDate start, LocalDate finish);
}
