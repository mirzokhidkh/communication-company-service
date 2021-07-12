package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.SimcardDto;
import uz.mk.communicationcompanyservice.payload.TariffWithDataStatics;
import uz.mk.communicationcompanyservice.service.BranchOfficeService;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    BranchOfficeService branchOfficeService;


    @PostMapping("/buySimcard")
    public HttpEntity<?> buySimcard(@RequestBody SimcardDto simcardDto) {
        ApiResponse response = branchOfficeService.buySimcard(simcardDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 409).body(response);
    }

    @GetMapping("/getAllBuyingTariffsStatics")
    public HttpEntity<?> getAllBuyingTariffs() {
        List<TariffWithDataStatics> tariffWithDataStaticsList = branchOfficeService.getAllBuyingTariffs();
        return ResponseEntity.ok(tariffWithDataStaticsList);
    }
}
