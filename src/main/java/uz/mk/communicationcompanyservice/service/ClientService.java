package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mk.communicationcompanyservice.entity.*;
import uz.mk.communicationcompanyservice.entity.enums.ClientMoveTypeName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.*;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

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

    public ApiResponse checkBalance() {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ApiResponse("Balance", true, principalSimCard.getBalance());
    }

    @Transactional
    public ApiResponse sendMessage(Integer numberOfSms) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
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
                : (oldNumberOfSms + " SMS in limit and " + amountBalance + " sum were deducted from balance for " + Math.abs(amount) + " SMS (" + smsPrice + " sum per 1 SMS)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.SENT_MESSAGE);
        Detail detail = CommonUtils.createDetail("User sent message", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);
    }

    @Transactional
    public ApiResponse call(Integer minutes) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
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
                : (oldMinutes + " minutes in limit and " + amountBalance + " sum were deducted from balance for " + Math.abs(newMinutes) + " minutes (" + outgoingCallPrice + " sum per 1 Minute)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.CALLED);
        Detail detail = CommonUtils.createDetail("User called", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);

    }

    @Transactional
    public ApiResponse useInternet(Double amountOfMb) {
        Simcard principalSimCard = (Simcard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID simCardId = principalSimCard.getId();

        Optional<Simcard> optionalSimcard = simcardRepository.findById(simCardId);
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
                : (oldMb + " MB in limit and " + amountBalance + " sum were deducted from balance for " + Math.abs(newAmountMb) + " MB (" + trafficPrice + " sum per 1 MB)"));

        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(ClientMoveTypeName.USED_THE_INTERNET);
        Detail detail = CommonUtils.createDetail("User used the internet", description, clientMoveType, simcard);
        detailRepository.save(detail);

        return new ApiResponse(description, true);
    }

}
