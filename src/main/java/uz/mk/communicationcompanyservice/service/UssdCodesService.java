package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.Package;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.entity.enums.ServiceTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.*;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class UssdCodesService {
    @Autowired
    UssdRepository ussdRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    SimcardSetRepository simcardSetRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    ExtraServiceRepository extraServiceRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;

    public List<Ussd> getAllUssdCodes() {
        List<Ussd> ussdList = ussdRepository.findAll();
        return ussdList;
    }

    public Tariff getTariffByUserId(UUID userId) {
        Simcard simcard = simcardRepository.getByClientId(userId);
        Tariff tariff = simcard.getTariff();
        return tariff;
    }

    @Transactional
    public ApiResponse changeTariff(Integer tariffId, UUID simcardId) {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isStaffAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_STAFF);

        boolean isClientAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_CLIENT);


        if (!(isStaffAuthority || isClientAuthority)) {
            return new ApiResponse("You don't have the authority", false);
        }

        Tariff tariff = tariffRepository.findById(tariffId).get();
        Double switchCostAmount = tariff.getSwitchCost();

        Simcard simcard = simcardRepository.findById(simcardId).get();

        if (simcard.getBalance() < switchCostAmount) {
            return new ApiResponse("You don't have enough money to switch to tariff", false);
        }


        simcard.setBalance(simcard.getBalance() - switchCostAmount);
        simcard.setTariff(tariff);
        TariffSet tariffSet = tariff.getTariffSet();
        SimcardSet simcardSet = simcard.getSimcardSet();
        simcardSet.setMb(tariffSet.getMb());
        simcardSet.setSms(tariffSet.getSms());
        simcardSet.setMinute(tariffSet.getMinute());
        simcardSetRepository.save(simcardSet);

        simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.CHANGED_TARIFF);
        Detail detail = CommonUtils.createDetail("User changed tariff", "", clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse("Successfully switched to tariff", true);
    }

    @Transactional
    public ApiResponse buyPackage(Integer packageId, UUID simcardId) {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isStaffAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_STAFF);

        boolean isClientAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_CLIENT);


        if (!(isStaffAuthority || isClientAuthority)) {
            return new ApiResponse("You don't have the authority", false);
        }

        Package buyingPackage = packageRepository.getById(packageId);
        Simcard simcard = simcardRepository.getById(simcardId);

        Double packagePrice = buyingPackage.getPrice();
        if (simcard.getBalance() < packagePrice) {
            return new ApiResponse("You don't have enough money to buy such a package", false);
        }

        List<Package> packageList = simcard.getCurrentPackage();

        ServiceTypeName serviceTypeName = buyingPackage.getServiceType().getServiceTypeName();
        String packageValue = buyingPackage.getValue();
        SimcardSet simcardSet = simcard.getSimcardSet();


        if (packageList.size() != 0) {
            packageList.removeIf(aPackage -> aPackage.getServiceType().getServiceTypeName().equals(serviceTypeName));
        }

        switch (serviceTypeName.name()) {
            case "MB":
                Double mb = simcardSet.getMb();
                simcardSet.setMb(mb + Double.parseDouble(packageValue));
                break;
            case "SMS":
                Integer sms = simcardSet.getSms();
                simcardSet.setSms(sms + Integer.parseInt(packageValue));
                break;
            case "MINUTE":
                Integer minute = simcardSet.getMinute();
                simcardSet.setMinute(minute + Integer.parseInt(packageValue));
                break;
        }

        simcardSetRepository.save(simcardSet);

        packageList.add(buyingPackage);
        simcard.setCurrentPackage(packageList);
        simcard.setBalance(simcard.getBalance() - packagePrice);
        simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.PURCHASED_PACKAGE);
        Detail detail = CommonUtils.createDetail("User bought the package", "", clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse("Successfully bought package", true);
    }

    @Transactional
    public ApiResponse buyExtraService(Integer serviceId, UUID simcardId) {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isStaffAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_STAFF);

        boolean isClientAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_CLIENT);


        if (!(isStaffAuthority || isClientAuthority)) {
            return new ApiResponse("You don't have the authority", false);
        }

        ExtraService extraService = extraServiceRepository.getById(serviceId);
        Simcard simcard = simcardRepository.getById(simcardId);

        Double servicePrice = extraService.getPrice();
        if (simcard.getBalance() < servicePrice) {
            return new ApiResponse("You don't have enough money to buy such a service", false);
        }

        List<ExtraService> serviceList = simcard.getCurrentService();

        serviceList.add(extraService);
        simcard.setCurrentService(serviceList);
        simcard.setBalance(simcard.getBalance() - servicePrice);
        simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.PURCHASED_EXTRA_SERVICE);
        Detail detail = CommonUtils.createDetail("User bought the extra service", "", clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse("Successfully bought service", true);
    }


}
