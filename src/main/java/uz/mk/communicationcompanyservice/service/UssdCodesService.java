package uz.mk.communicationcompanyservice.service;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Simcard;
import uz.mk.communicationcompanyservice.entity.Tariff;
import uz.mk.communicationcompanyservice.entity.Ussd;
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
    ServiceRepository serviceRepository;

    public List<Ussd> getAllUssdCodes() {
        List<Ussd> ussdList = ussdRepository.findAll();
        return ussdList;
    }

    public Tariff getTariffByUserId(UUID userId) {
        Simcard simcard = simcardRepository.getByClientId(userId);
        Tariff tariff = simcard.getTariff();
        return tariff;
    }

    public ApiResponse changeTariff(Integer id, UUID simcardId){
        return null;
    }



}
