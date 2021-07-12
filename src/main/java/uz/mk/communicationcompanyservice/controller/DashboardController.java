package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mk.communicationcompanyservice.entity.Income;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.SimcardDto;
import uz.mk.communicationcompanyservice.payload.TariffWithDataStatics;
import uz.mk.communicationcompanyservice.repository.IncomeRepository;
import uz.mk.communicationcompanyservice.service.BranchOfficeService;
import uz.mk.communicationcompanyservice.service.IncomeService;

import java.util.List;

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
        List<Income> incomes = incomeService.getIncomesByDaily();
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/getIncomesByMonthly")
    public HttpEntity<?> getIncomesByMonthly() {
        List<Income> incomes = incomeService.getIncomesByMonthly();
        return ResponseEntity.ok(incomes);
    }

}
