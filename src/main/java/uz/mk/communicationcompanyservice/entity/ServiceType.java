package uz.mk.communicationcompanyservice.entity;

import lombok.Data;
import uz.mk.communicationcompanyservice.entity.enums.ServiceTypeName;

import javax.persistence.*;

@Data
@Entity
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ServiceTypeName serviceTypeName;
}
