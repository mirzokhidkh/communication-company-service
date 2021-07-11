package uz.mk.communicationcompanyservice.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.PurchasedItemTypeName;
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


    public static Integer generateCode() {
        return new Random().nextInt((999999 - 100000) + 1) + 100000;
    }
}
