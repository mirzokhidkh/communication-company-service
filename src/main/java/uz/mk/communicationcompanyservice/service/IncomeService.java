package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Income;
import uz.mk.communicationcompanyservice.repository.IncomeRepository;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    public Page<Income> getIncomesByDaily() {

        return null;
    }
}
