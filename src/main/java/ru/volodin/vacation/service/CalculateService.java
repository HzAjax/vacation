package ru.volodin.vacation.service;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameter;
import de.jollyday.ManagerParameters;
import org.springframework.stereotype.Service;
import ru.volodin.vacation.entity.CalculateDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SuppressWarnings({"unused"})
@Service
public class CalculateService {

    public Float getVacationCompensationEazy(float salary, int dayCount){
        return salary * (float) dayCount;
    }

    public Float getVacationCompensationHard(CalculateDTO cacl){
        HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.RUSSIA));
        long daysBetween = ChronoUnit.DAYS.between(cacl.getDayStart(), cacl.getDayFinish());
        int holidaysCount = holidayManager.getHolidays(cacl.getDayStart(),cacl.getDayFinish()).size();
        return ((int) daysBetween - holidaysCount) * cacl.getSalary();
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        HolidayManager manager = HolidayManager.getInstance(HolidayCalendar.RUSSIA);
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
