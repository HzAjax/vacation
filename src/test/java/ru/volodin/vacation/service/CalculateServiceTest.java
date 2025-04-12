package ru.volodin.vacation.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CalculateServiceTest {

    private final CalculateServiceImpl calculateService = new CalculateServiceImpl();

    @Test
    void testGetVacationCompensationEazy_validInput() {
        float salary = 73000f;
        int days = 14;

        float result = calculateService.getVacationCompensationEazy(salary, days);

        assertTrue(result > 0);
        assertEquals((salary / 365f) * days, result, 0.01f);
    }

    @Test
    void testGetVacationCompensationEazy_invalidSalary() {
        assertThrows(IllegalArgumentException.class, () ->
                calculateService.getVacationCompensationEazy(0, 10));
    }

    @Test
    void testGetVacationCompensationEazy_invalidDays() {
        assertThrows(IllegalArgumentException.class, () ->
                calculateService.getVacationCompensationEazy(10000, 0));
    }

    @Test
    void testGetVacationCompensationHard_validInput() {
        float salary = 90000f;
        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate end = LocalDate.of(2025, 5, 14); // 14 дней

        Float result = calculateService.getVacationCompensationHard(salary, start, end);

        assertNotNull(result);
        assertTrue(result > 0);
    }

    @Test
    void testGetVacationCompensationHard_invalidSalary() {
        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate end = LocalDate.of(2025, 5, 14);

        assertThrows(IllegalArgumentException.class, () ->
                calculateService.getVacationCompensationHard(0, start, end));
    }

    @Test
    void testGetVacationCompensationHard_invalidDateRange() {
        LocalDate start = LocalDate.of(2025, 5, 15);
        LocalDate end = LocalDate.of(2025, 5, 10);

        assertThrows(IllegalArgumentException.class, () ->
                calculateService.getVacationCompensationHard(50000f, start, end));
    }

    @Test
    void testGetHolidaysCountInGap() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 10);

        int count = calculateService.getHolidaysCountInGap(start, end);

        assertTrue(count >= 0);
    }

    @Test
    void testGetHolidaysCountInYear() {
        LocalDate date = LocalDate.of(2025, 1, 1);

        int count = calculateService.getHolidaysCountInYear(date);

        assertTrue(count > 0);
    }

    @Test
    void testGetWeekendDays() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate end = LocalDate.of(2025, 3, 10);

        Set<LocalDate> weekends = calculateService.getWeekendDays(start, end, new java.util.HashSet<>());

        assertNotNull(weekends);
        assertTrue(weekends.size() >= 2); // как минимум одна суббота и воскресенье
    }
}
