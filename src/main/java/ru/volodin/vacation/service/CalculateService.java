package ru.volodin.vacation.service;

import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.HolidayManager;
import de.focus_shift.jollyday.core.ManagerParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static de.focus_shift.jollyday.core.HolidayCalendar.RUSSIA;

@SuppressWarnings({"unused"})
@Service
public class CalculateService {

    private final int YEAR_DAYS_LEAP = 366;
    private final int YEAR_DAYS = 365;

    public Float getVacationCompensationEazy(float salary, int dayCount) {
        if (salary <= 0 || dayCount <= 0) {
            throw new IllegalArgumentException("Неверно указана зарплата или дата.");
        }

        return (salary / YEAR_DAYS) * (float) dayCount;
    }

    public Float getVacationCompensationHard(float salary, LocalDate start, LocalDate finish) {
        if (salary <= 0 || finish.isAfter(start)) {
            throw new IllegalArgumentException("Неверно указана зарплата или дата.");
        }

        int countHolidaysInGap = getHolidaysCountInGap(start, finish);
        int countHolidaysInYear = getHolidaysCountInYear(start);

        int countDays;
        if (start.getYear() % 4 == 0) {
            countDays = YEAR_DAYS_LEAP;
        } else {
            countDays = YEAR_DAYS;
        }

        int daysBetween = (int) ChronoUnit.DAYS.between(start, finish) + 1;

        return ((salary / (countDays - countHolidaysInYear) * (daysBetween - countHolidaysInGap)));
    }

    public int getHolidaysCountInGap(LocalDate start, LocalDate finish) {
        final HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(RUSSIA));
        final Set<Holiday> holidaysInGap = holidayManager.getHolidays(start, finish);

        return getWeekendDays(start, finish, holidaysInGap).size();
    }

    public int getHolidaysCountInYear(LocalDate start) {
        final HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(RUSSIA));
        final Set<Holiday> holidaysInGap = holidayManager.getHolidays(Year.of(start.getYear()));

        return getWeekendDays(LocalDate.of(start.getYear(), 1, 1),
                LocalDate.of(start.getYear(), 12, 31),
                holidaysInGap)
                .size();
    }

    public Set<LocalDate> getWeekendDays(LocalDate start, LocalDate finish, Set<Holiday> holidaysInGap) {
        Set<LocalDate> holidays = new HashSet<>();

        for (Holiday h : holidaysInGap) {
            holidays.add(h.getActualDate());
        }

        LocalDate date = start;
        while (!date.isAfter(finish)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                holidays.add(date);
            }
            date = date.plusDays(1);
        }

        return holidays;
    }
}
