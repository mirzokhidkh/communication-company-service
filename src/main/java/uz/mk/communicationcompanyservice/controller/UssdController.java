package uz.mk.communicationcompanyservice.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import uz.mk.communicationcompanyservice.entity.Tariff;
import uz.mk.communicationcompanyservice.entity.Ussd;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.service.UssdCodesService;

import java.util.List;
import java.util.UUID;

@RepositoryRestController
@RequestMapping("/ussdCodes")
public class UssdController {

    @Autowired
    UssdCodesService ussdCodesService;


    @GetMapping
    public HttpEntity<?> getAllUssdCodes() {
        List<Ussd> allUssdCodes = ussdCodesService.getAllUssdCodes();
        return ResponseEntity.ok(allUssdCodes);
    }

    @GetMapping("/tariffByClientId")
    public HttpEntity<?> getTariffByUserId(@RequestParam String userId) {
        Tariff tariff = ussdCodesService.getTariffByUserId(UUID.fromString(userId));
        return ResponseEntity.ok(tariff);
    }


    @GetMapping("/changeTariff")
    public HttpEntity<?> changeTariff(@RequestParam String simcardId, @RequestParam Integer tariffId) {
        ApiResponse response = ussdCodesService.changeTariff(tariffId, UUID.fromString(simcardId));
        return ResponseEntity.status(response.isStatus() ? 202 : 409).body(response);
    }

    @GetMapping("/buyPackage")
    public HttpEntity<?> buyPackage(@RequestParam String simcardId, @RequestParam Integer packageId) {
        ApiResponse response = ussdCodesService.buyPackage(packageId, UUID.fromString(simcardId));
        return ResponseEntity.status(response.isStatus() ? 202 : 409).body(response);
    }

    @GetMapping("/buyExtraService")
    public HttpEntity<?> buyExtraService(@RequestParam String simcardId, @RequestParam Integer serviceId) {
        ApiResponse response = ussdCodesService.buyExtraService(serviceId, UUID.fromString(simcardId));
        return ResponseEntity.status(response.isStatus() ? 202 : 409).body(response);
    }

}
