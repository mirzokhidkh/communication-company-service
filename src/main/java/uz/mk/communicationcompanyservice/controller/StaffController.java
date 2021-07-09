package uz.mk.communicationcompanyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.UserDto;
import uz.mk.communicationcompanyservice.service.StaffService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class StaffController {

    @Autowired
    StaffService staffService;


    @PutMapping("/{id}")
    public HttpEntity<?> editById(@PathVariable String id, @RequestBody UserDto userDto) {
        ApiResponse response = staffService.editById(UUID.fromString(id), userDto);
        return ResponseEntity.status(response.isStatus() ? 202 : 409).body(response);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable String id) {
        ApiResponse response = staffService.deleteById(UUID.fromString(id));
        return ResponseEntity.status(response.isStatus() ? 204 : 409).body(response);
    }

}
