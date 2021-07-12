package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.*;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimcardRepository simcardRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    SimcardSetRepository simcardSetRepository;

    @Autowired
    ClientMoveTypeRepository clientMoveTypeRepository;

    @Transactional
    public ApiResponse sendMessage(UUID simcardId, Integer numberOfSms) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(simcardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();

        Integer oldNumberOfSms = simcardSet.getSms();
        Integer amount = oldNumberOfSms - numberOfSms;
        Double amountBalance = 0.0;
        Double smsPrice = simcard.getTariff().getTariffSet().getSmsPrice();
        if (amount < 0) {
            amountBalance = smsPrice * Math.abs(amount);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setSms(0);
        } else {
            simcardSet.setSms(amount);
        }

        simcardSetRepository.save(simcardSet);

        String description = "User sent message " + (amount >= 0
                ? (numberOfSms + " SMS in limit")
                : (oldNumberOfSms + " SMS in limit and " + amountBalance + " so'm were deducted from balance for " + Math.abs(amount) + " SMS (" + smsPrice + " so'm per 1 SMS)"));
        saveDetail("User sent message", description, ClientMoveTypeName.SENT_MESSAGE, simcard);

        return new ApiResponse(description, true);
    }

    @Transactional
    public ApiResponse call(UUID simcardId, Integer minutes) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(simcardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Integer oldMinutes = simcardSet.getMinute();
        int newMinutes = oldMinutes - minutes;
        Double amountBalance = 0.0;
        Double outgoingCallPrice = simcard.getTariff().getTariffSet().getOutgoingCallPrice();
        if (newMinutes < 0) {
            amountBalance = outgoingCallPrice * Math.abs(newMinutes);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setMinute(0);

        } else {
            simcardSet.setMinute(newMinutes);
        }
        simcardSetRepository.save(simcardSet);
        String description = "User called for " + (newMinutes >= 0
                ? (minutes + " minutes in limit")
                : (oldMinutes + " minutes in limit and " + amountBalance + " so'm were deducted from balance for " + Math.abs(newMinutes) + " minutes (" + outgoingCallPrice + " so'm per 1 Minute)"));
        saveDetail("User called", description, ClientMoveTypeName.CALLED, simcard);

        return new ApiResponse(description, true);

    }

    @Transactional
    public ApiResponse useInternet(UUID simcardId, Double amountOfMb) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(simcardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Double oldMb = simcardSet.getMb();
        Double newAmountMb = oldMb - amountOfMb;
        Double amountBalance = 0.0;
        Double trafficPrice = simcard.getTariff().getTariffSet().getInternetTrafficPrice();
        if (newAmountMb < 0) {
            amountBalance = trafficPrice * Math.abs(newAmountMb);
            simcard.setBalance(simcard.getBalance() - amountBalance);
            simcardRepository.save(simcard);
            simcardSet.setMb(0.0);
        } else {
            simcardSet.setMb(newAmountMb);
        }
        simcardSetRepository.save(simcardSet);
        String description = "User used " + (newAmountMb >= 0
                ? (amountOfMb + " MB in limit")
                : (oldMb + " MB in limit and " + amountBalance + " so'm were deducted from balance for " + Math.abs(newAmountMb) + " MB (" + trafficPrice + " so'm per 1 MB)"));
        saveDetail("User used the internet", description, ClientMoveTypeName.USED_THE_INTERNET, simcard);

        return new ApiResponse(description, true);
    }


    private void saveDetail(String name, String description, ClientMoveTypeName clientMoveTypeName, Simcard simcard) {
        Detail detail = new Detail();
        detail.setName(name);
        detail.setDescription(description);
        detail.setSimcard(simcard);
        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(clientMoveTypeName);
        detail.setClientMoveType(clientMoveType);
        detailRepository.save(detail);
    }

}
