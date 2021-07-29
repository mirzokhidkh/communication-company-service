package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Income;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.IncomeRepository;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    public ApiResponse getIncomesByDaily() {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_DIRECTOR);
        boolean isBranchDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_DIRECTOR);
        boolean isBranchManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_MANAGER);

        if (!(isDirectorAuthority || isBranchDirectorAuthority || isBranchManagerAuthority)) {
            return new ApiResponse("You don't have the authority", false);
        }

        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
        LocalDateTime endOfDate = LocalTime.MAX.atDate(localDate);
        Timestamp minDate = Timestamp.valueOf(startOfDay);
        Timestamp maxDate = Timestamp.valueOf(endOfDate);
        List<Income> incomes = incomeRepository.findAllByDateBetween(minDate, maxDate);
        return new ApiResponse("Daily incomes", true, incomes);
    }

    public ApiResponse getIncomesByMonthly() {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_DIRECTOR);
        boolean isBranchDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_DIRECTOR);
        boolean isBranchManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_MANAGER);

        if (!(isDirectorAuthority || isBranchDirectorAuthority || isBranchManagerAuthority)) {
            return new ApiResponse("You don't have the authority", false);
        }

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

        return new ApiResponse("Monthly incomes", true, incomes);
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


