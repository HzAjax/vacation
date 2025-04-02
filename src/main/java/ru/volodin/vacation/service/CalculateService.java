package ru.volodin.vacation.service;

import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.HolidayManager;
import de.focus_shift.jollyday.core.ManagerParameters;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Set;

import static de.focus_shift.jollyday.core.HolidayCalendar.RUSSIA;

@SuppressWarnings({"unused"})
@Service
public class CalculateService {

    public Float getVacationCompensationEazy(float salary, int dayCount){
        return (salary / 365) * (float) dayCount;
    }

    public Set<Holiday> getVacationCompensationHard(float salary){
        final HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(RUSSIA));
        final Set<Holiday> holidays = holidayManager.getHolidays(Year.of(2022));
        //long daysBetween = ChronoUnit.DAYS.between(dayStart, dayFinish);
        System.out.println(holidays.toString());
        return holidays;
    }
}
