package uz.mk.communicationcompanyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RegisterDto {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String passportId;
    @NotNull
    private Set<Integer> roles;
}
