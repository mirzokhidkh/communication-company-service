package uz.mk.communicationcompanyservice.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
