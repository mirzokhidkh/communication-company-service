package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.SimcardDto;
import uz.mk.communicationcompanyservice.payload.TariffWithDataStatics;
import uz.mk.communicationcompanyservice.repository.*;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.util.List;

@Service
public class BranchOfficeService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    SimcardSetRepository simcardSetRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;


    @Transactional
    public ApiResponse buySimcard(SimcardDto simcardDto) {
        Simcard simcard = new Simcard();
        simcard.setCompanyCode(simcardDto.getCompanyCode());
        simcard.setNumber(simcardDto.getNumber());
        simcard.setBalance(simcardDto.getBalance());
        simcard.setStatus(simcardDto.isStatus());


        User client = userRepository.findById(simcardDto.getClientId()).get();
        simcard.setClient(client);

        Tariff tariff = tariffRepository.findById(simcardDto.getTariffId()).get();
        simcard.setTariff(tariff);

        TariffSet tariffSet = tariff.getTariffSet();
        SimcardSet simcardSet = new SimcardSet();
        simcardSet.setMb(tariffSet.getMb());
        simcardSet.setSms(tariffSet.getSms());
        simcardSet.setMinute(tariffSet.getMinute());
        SimcardSet savedSimcardSet = simcardSetRepository.save(simcardSet);
        simcard.setSimcardSet(savedSimcardSet);

        Simcard savedSimcard = simcardRepository.save(simcard);

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.PURCHASED_SIMCARD);
        PaymentType paymentType = paymentTypeRepository.findById(simcardDto.getPaymentTypeId()).get();
        Income income = CommonUtils.createIncome(simcardDto.getPrice(), savedSimcard, clientMoveType, paymentType);
        incomeRepository.save(income);

        return new ApiResponse("Successfully bought simcard", true, savedSimcard);
    }


    public List<TariffWithDataStatics> getAllBuyingTariffs() {
        List<TariffWithDataStatics> tariffWithDataStaticsList = simcardRepository.findAllBuyingTariffs();
        return tariffWithDataStaticsList;
    }



}
