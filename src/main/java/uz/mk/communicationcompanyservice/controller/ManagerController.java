package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.mk.communicationcompanyservice.payload.ServiceStaticsDto;
import uz.mk.communicationcompanyservice.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;


    @GetMapping("/buyingServcesStatics")
    public HttpEntity<?> getAllBuyingServicesStatics() {
        List<ServiceStaticsDto> popularServices = managerService.getAllBuyingServicesStatics();
        return ResponseEntity.ok(popularServices);
    }
}
