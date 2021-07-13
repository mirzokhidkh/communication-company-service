package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.ServiceWithDataStatics;
import uz.mk.communicationcompanyservice.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;


    @GetMapping("/buyingServcesStatics")
    public HttpEntity<?> getAllBuyingServicesStatics() {
        ApiResponse response = managerService.getAllBuyingServicesStatics();
        return ResponseEntity.status(response.isStatus() ? 200 : 403).body(response);
    }
}
