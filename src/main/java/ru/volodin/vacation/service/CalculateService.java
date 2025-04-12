package ru.volodin.vacation.service;

import java.time.LocalDate;

public interface CalculateService {
    Float getVacationCompensationEazy(float salary, int dayCount);
    Float getVacationCompensationHard(float salary, LocalDate start, LocalDate finish);
}
