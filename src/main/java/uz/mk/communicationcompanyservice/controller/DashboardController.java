package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.SimcardDto;
import uz.mk.communicationcompanyservice.service.BranchOfficeService;
import uz.mk.communicationcompanyservice.service.IncomeService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    BranchOfficeService branchOfficeService;

    @Autowired
    IncomeService incomeService;


    @PostMapping("/buySimcard")
    public HttpEntity<?> buySimcard(@RequestBody SimcardDto simcardDto) {
        ApiResponse response = branchOfficeService.buySimcard(simcardDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 409).body(response);
    }

    @GetMapping("/getAllBuyingTariffsStatics")
    public HttpEntity<?> getAllBuyingTariffs() {
        ApiResponse response = branchOfficeService.getAllBuyingTariffs();
        return ResponseEntity.status(response.isStatus() ? 200 : 430).body(response);
    }

    @GetMapping("/getIncomesByDaily")
    public HttpEntity<?> getIncomesByDaily() {
        ApiResponse response = incomeService.getIncomesByDaily();
        return ResponseEntity.status(response.isStatus() ? 200 : 403).body(response);
    }

    @GetMapping("/getIncomesByMonthly")
    public HttpEntity<?> getIncomesByMonthly() {
        ApiResponse response = incomeService.getIncomesByMonthly();
        return ResponseEntity.status(response.isStatus() ? 200 : 403).body(response);
    }

}
