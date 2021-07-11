package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.payload.ServiceWithDataStatics;
import uz.mk.communicationcompanyservice.repository.InfoAndEntertainmentServiceRepository;
import uz.mk.communicationcompanyservice.repository.SimcardRepository;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    InfoAndEntertainmentServiceRepository serviceRepository;

    @Autowired
    SimcardRepository simcardRepository;

    public List<ServiceWithDataStatics> getAllBuyingServicesStatics(){
        List<ServiceWithDataStatics> popularServices = simcardRepository.findAllBuyingServicesStatics();
        return popularServices;
    }
}