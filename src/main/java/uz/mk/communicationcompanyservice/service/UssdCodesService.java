package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.Package;
import uz.mk.communicationcompanyservice.entity.enums.ServiceTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.*;

import java.util.List;
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
    TariffRepository tariffRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    InfoAndEntertainmentServiceRepository extraServiceRepository;

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
        Tariff tariff = tariffRepository.getById(tariffId);
        Double switchCostAmount = tariff.getSwitchCost();

        Simcard simcard = simcardRepository.getById(simcardId);

        if (simcard.getBalance() < switchCostAmount) {
            return new ApiResponse("You don't have enough money to switch to tariff", false);
        }

        simcard.setBalance(simcard.getBalance() - switchCostAmount);
        simcard.setTariff(tariff);
        simcardRepository.save(simcard);

        return new ApiResponse("Successfully switched to tariff", true);
    }

    @Transactional
    public ApiResponse buyPackage(Integer packageId, UUID simcardId) {

        Package buyingPackage = packageRepository.getById(packageId);
        Simcard simcard = simcardRepository.getById(simcardId);
        Tariff tariff = simcard.getTariff();

        Double packagePrice = buyingPackage.getPrice();
        if (simcard.getBalance() < packagePrice) {
            return new ApiResponse("You don't have enough money to buy such a package", false);
        }

        List<Package> packageList = simcard.getCurrentPackage();

        ServiceTypeName serviceTypeName = buyingPackage.getServiceType().getServiceTypeName();
        String packageValue = buyingPackage.getValue();
        TariffSet tariffSet = tariff.getTariffSet();


        if (packageList.size() != 0) {
            packageList.removeIf(aPackage -> aPackage.getServiceType().getServiceTypeName().equals(serviceTypeName));
        }

        switch (serviceTypeName.name()) {
            case "MB":
                Double mb = tariffSet.getMb();
                tariffSet.setMb(mb + Double.parseDouble(packageValue));
                break;
            case "SMS":
                Integer sms = tariffSet.getSms();
                tariffSet.setSms(sms + Integer.parseInt(packageValue));
                break;
            case "MINUTE":
                Integer minute = tariffSet.getMinute();
                tariffSet.setMinute(minute + Integer.parseInt(packageValue));
                break;
        }

        packageList.add(buyingPackage);
        simcard.setCurrentPackage(packageList);
        simcard.setBalance(simcard.getBalance() - packagePrice);
        simcardRepository.save(simcard);

        return new ApiResponse("Successfully bought package", true);
    }


    @Transactional
    public ApiResponse buyExtraService(Integer serviceId, UUID simcardId) {
        InfoAndEntertainmentService extraService = extraServiceRepository.getById(serviceId);
        Simcard simcard = simcardRepository.getById(simcardId);

        Double servicePrice = extraService.getPrice();
        if (simcard.getBalance() < servicePrice) {
            return new ApiResponse("You don't have enough money to buy such a service", false);
        }

        List<InfoAndEntertainmentService> serviceList = simcard.getCurrentService();

        serviceList.add(extraService);
        simcard.setCurrentService(serviceList);
        simcard.setBalance(simcard.getBalance() - servicePrice);
        simcardRepository.save(simcard);

        return new ApiResponse("Successfully bought service", true);
    }


}
