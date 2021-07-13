package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.ServiceWithDataStatics;
import uz.mk.communicationcompanyservice.repository.ExtraServiceRepository;
import uz.mk.communicationcompanyservice.repository.SimcardRepository;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ManagerService {

    @Autowired
    ExtraServiceRepository serviceRepository;

    @Autowired
    SimcardRepository simcardRepository;

    public ApiResponse getAllBuyingServicesStatics() {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");


        boolean isBranchManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, "MANAGER");

        if (!isBranchManagerAuthority) {
            return new ApiResponse("You don't have the authority", false);
        }

        List<ServiceWithDataStatics> popularServices = simcardRepository.findAllBuyingServicesStatics();
        return new ApiResponse("Popular Services", true, popularServices);
    }
}
