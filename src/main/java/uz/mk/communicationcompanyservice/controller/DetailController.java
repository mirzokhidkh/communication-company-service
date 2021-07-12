package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mk.communicationcompanyservice.service.DetailService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/detail")
public class DetailController {

    @Autowired
    DetailService detailService;

    @GetMapping("/detailsFile")
    public void downloadDetails( HttpServletResponse response) {
        detailService.downloadDetails(response);
    }
}
