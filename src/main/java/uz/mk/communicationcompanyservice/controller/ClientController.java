package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.service.ClientService;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/sentMessage")
    public HttpEntity<?> sentMessage(@RequestParam String simcardId, @RequestParam Integer numberOfSms) {
        ApiResponse response = clientService.sendMessage(UUID.fromString(simcardId), numberOfSms);
        return ResponseEntity.status(response.isStatus() ? 200 : 409).body(response);
    }

    @GetMapping("/call")
    public HttpEntity<?> call(@RequestParam String simcardId, @RequestParam Integer minutes) {
        ApiResponse response = clientService.call(UUID.fromString(simcardId), minutes);
        return ResponseEntity.status(response.isStatus() ? 200 : 409).body(response);
    }

    @GetMapping("/useInternet")
    public HttpEntity<?> useInternet(@RequestParam String simcardId, @RequestParam Double amountOfMb) {
        ApiResponse response = clientService.useInternet(UUID.fromString(simcardId), amountOfMb);
        return ResponseEntity.status(response.isStatus() ? 200 : 409).body(response);
    }


}
