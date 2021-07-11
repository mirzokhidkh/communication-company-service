package uz.mk.communicationcompanyservice.service;

import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.PurchasedItemTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.SimcardDto;
import uz.mk.communicationcompanyservice.repository.*;

@Service
public class BranchOfficeService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    PurchasedItemTypeRepository purchasedItemTypeRepository;

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
        Simcard savedSimcard = simcardRepository.save(simcard);

//        Income income = new Income();
//        income.setAmount(simcardDto.getPrice());
//        income.setClient(client);
//
//        PurchasedItemType purchasedItemType = purchasedItemTypeRepository.findByName(PurchasedItemTypeName.SIMCARD);
//        income.setPurchasedItemType(purchasedItemType);
//
//        PaymentType paymentType = paymentTypeRepository.getById(simcardDto.getPaymentTypeId());
//        income.setPaymentType(paymentType);
//        Income savedIncome = incomeRepository.save(income);

        createIncome(simcardDto.getPrice(), client, PurchasedItemTypeName.SIMCARD, simcardDto.getPaymentTypeId());

        return new ApiResponse("Successfully bought simcard", true, savedSimcard);
    }

    private void createIncome(Double price, User client, PurchasedItemTypeName purchasedItemTypeName, Integer paymentTypeId) {
        Income income = new Income();
        income.setAmount(price);
        income.setClient(client);
        PurchasedItemType purchasedItemType = purchasedItemTypeRepository.findByName(purchasedItemTypeName);
        income.setPurchasedItemType(purchasedItemType);
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId).get();
        income.setPaymentType(paymentType);
        incomeRepository.save(income);
    }


}
