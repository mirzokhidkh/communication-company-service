package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Income;
import uz.mk.communicationcompanyservice.repository.IncomeRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getIncomesByDaily() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
        LocalDateTime endOfDate = LocalTime.MAX.atDate(localDate);
        Timestamp minDate = Timestamp.valueOf(startOfDay);
        Timestamp maxDate = Timestamp.valueOf(endOfDate);
        List<Income> incomes = incomeRepository.findAllByDateBetween(minDate, maxDate);
        return incomes;
    }

    public List<Income> getIncomesByMonthly() {
        Date begining, end;

        Calendar calendar1 = getCalendarForNow();
        calendar1.set(Calendar.DAY_OF_MONTH,
                calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar1);
        begining = calendar1.getTime();

        Calendar calendar2 = getCalendarForNow();
        calendar2.set(Calendar.DAY_OF_MONTH,
                calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndOfDay(calendar2);
        end = calendar2.getTime();


        List<Income> incomes = incomeRepository.findAllByDateBetween(new Timestamp(begining.getTime()), new Timestamp(end.getTime()));

        return incomes;
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }


}


