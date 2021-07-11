package uz.mk.communicationcompanyservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mk.communicationcompanyservice.entity.Category;
import uz.mk.communicationcompanyservice.entity.ServiceType;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceWithDataStatics {
    private Integer serviceId;

    private String name;

    private String value;

    private String description;

    private Double price = 0.0;

    private Integer categoryId;

    private Integer serviceTypeId;

    private Long numberOfUses;

    public ServiceWithDataStatics(Integer serviceId, String name, Long numberOfUses) {
        this.serviceId = serviceId;
        this.name = name;
        this.numberOfUses = numberOfUses;
    }
}
