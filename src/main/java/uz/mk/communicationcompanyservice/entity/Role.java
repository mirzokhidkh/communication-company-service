package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;

import javax.persistence.*;

@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }

}
