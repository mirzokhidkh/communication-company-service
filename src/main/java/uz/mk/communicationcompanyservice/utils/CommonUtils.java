package uz.mk.communicationcompanyservice.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CommonUtils {

    public static Map<String, Object> getPrincipalAndRoleFromSecurityContextHolder() {
        Map<String, Object> map = new HashMap<String, Object>();

        User principalUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        principalUser = (User) authentication.getPrincipal();

        Set<Role> principalUserRoles = principalUser.getRole();
        map.put("principalUser", principalUser);
        map.put("principalUserRoles", principalUserRoles);
        return map;
    }

    public static boolean isExistsAuthority(Set<Role> roles, RoleName roleName) {
        return roles.stream().anyMatch(role -> role.getRoleName().equals(roleName));
    }

    public static boolean isExistsAuthority(Set<Role> roles, String roleName) {
        return roles.stream().anyMatch(role -> role.getRoleName().name().contains(roleName));
    }

    public static Income createIncome(Double price, Simcard simcard, ClientMoveType clientMoveType, PaymentType paymentType) {
        Income income = new Income();
        income.setAmount(price);
        income.setSimcard(simcard);
        income.setClientMoveType(clientMoveType);
        income.setPaymentType(paymentType);
        return income;
    }

    public static Detail createDetail(String name, String description, ClientMoveType clientMoveType, Simcard simcard) {
        Detail detail = new Detail();
        detail.setName(name);
        detail.setDescription(description);
        detail.setSimcard(simcard);
        detail.setClientMoveType(clientMoveType);
        return detail;
    }


    public static Integer generateCode() {
        return new Random().nextInt((999999 - 100000) + 1) + 100000;
    }
}
