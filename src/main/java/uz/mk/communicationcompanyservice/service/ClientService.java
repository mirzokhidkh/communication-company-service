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
        if (amount < 0) {
            simcardSet.setSms(0);
        } else {
            simcardSet.setSms(amount);
        }

        simcardSetRepository.save(simcardSet);
        saveDetail("User sent message", ClientMoveTypeName.SENT_MESSAGE, simcard);

        return new ApiResponse("User have sent message" + (amount >= 0 ? numberOfSms : oldNumberOfSms) + " SMS", true);
    }

    @Transactional
    public ApiResponse call(UUID simcardId, Integer minutes) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(simcardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Integer oldMinutes = simcardSet.getMinute();
        int newMinutes = oldMinutes - minutes;
        if (newMinutes < 0) {
            simcardSet.setMinute(0);
        } else {
            simcardSet.setMinute(newMinutes);
        }
        simcardSetRepository.save(simcardSet);
        saveDetail("User called", ClientMoveTypeName.CALLED, simcard);

        return new ApiResponse("User call duration is " + (newMinutes >= 0 ? minutes : oldMinutes) + " minutes", true);
    }

    @Transactional
    public ApiResponse useInternet(UUID simcardId, Double amountOfMb) {
        Optional<Simcard> optionalSimcard = simcardRepository.findById(simcardId);
        Simcard simcard = optionalSimcard.get();
        SimcardSet simcardSet = simcard.getSimcardSet();
        Double oldMb = simcardSet.getMb();
        Double newAmountMb = oldMb - amountOfMb;
        if (newAmountMb < 0) {
            simcardSet.setMb(0.0);
        } else {
            simcardSet.setMb(newAmountMb);
        }
        simcardSetRepository.save(simcardSet);
        saveDetail("User used the internet", ClientMoveTypeName.USED_THE_INTERNET, simcard);

        return new ApiResponse("User used " + (newAmountMb >= 0 ? amountOfMb : oldMb) + " mb", true);
    }


    private void saveDetail(String name, ClientMoveTypeName clientMoveTypeName, Simcard simcard) {
        Detail detail = new Detail();
        detail.setName(name);
        detail.setSimcard(simcard);
        ClientMoveType clientMoveType = clientMoveTypeRepository.findByName(clientMoveTypeName);
        detail.setClientMoveType(clientMoveType);
        detailRepository.save(detail);
    }

}
