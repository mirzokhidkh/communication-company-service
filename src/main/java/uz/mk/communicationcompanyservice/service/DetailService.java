package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Detail;
import uz.mk.communicationcompanyservice.repository.DetailRepository;

@Service
public class DetailService {
    @Autowired
    DetailRepository detailRepository;

    public Page<Detail> downloadDetailsPDF(){

        return null;
    }
}
