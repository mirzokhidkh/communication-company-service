package uz.mk.communicationcompanyservice.payload;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.Role;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UserDto {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Set<Integer> roleIds;

    private String passportId;

    private boolean status;
}
